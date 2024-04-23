package roomescape.domain.repository;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
@Primary
public class H2ReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public H2ReservationRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAllReservations() {
        String sql = "SELECT * FROM reservation";
        RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
            Reservation reservation = new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("time").toLocalTime()
            );
            return reservation;
        };

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Reservation createReservation(Reservation requestReservation) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", requestReservation.getName())
                .addValue("date", requestReservation.getDate())
                .addValue("time", requestReservation.getTime());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return new Reservation(
                id, requestReservation.getName(),
                requestReservation.getDate(), requestReservation.getTime());
    }

    @Override
    public void deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
