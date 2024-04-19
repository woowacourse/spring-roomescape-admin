package roomescape.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> selectAll() {
        String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(sql, this::rowMapper);
    }

    private Reservation rowMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        Reservation reservation = new Reservation(
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
        reservation.initializeId(resultSet.getLong("id"));
        return reservation;
    }
}
