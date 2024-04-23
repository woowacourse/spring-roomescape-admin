package roomescape.dao;

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
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public long create(Reservation reservation) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time", reservation.getTime());
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public List<Reservation> getAll() {
        String sql = """
                SELECT
                r.id AS reservation_id,
                r.name,
                r.date,
                t.id AS time_id,
                t.start_at AS time_value
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                ON r.time_id = t.id
                """;
        RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
            long reservationId = resultSet.getLong("reservation_id");
            String name = resultSet.getString("name");
            String date = resultSet.getString("date");
            long timeId = resultSet.getLong("time_id");
            String startAt = resultSet.getString("time_value");
            return new Reservation(reservationId, name, date, new ReservationTime(timeId, startAt));
        };
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
