package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;

import java.util.List;

public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String findAllSql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(findAllSql,
                (resultSet, numRow) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")
                )
        );
    }
}
