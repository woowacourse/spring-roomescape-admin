package roomescape.repository;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationRepository {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        resultSet.getString("time_value")
                )
        );
        return reservation;
    };

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id")
                .usingColumns("name", "date", "time_id");
    }

    public List<Reservation> list() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value"
                + " FROM reservation as r inner join reservation_time as t on r.time_id = t.id";

        return jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER);
    }

    public Reservation insert(Reservation reservation) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.name())
                .addValue("date", reservation.date())
                .addValue("time_id", reservation.time().id());

        long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return new Reservation(id, reservation.name(), reservation.date(), reservation.time());
    }

    public void delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
