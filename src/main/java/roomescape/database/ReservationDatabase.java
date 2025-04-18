package roomescape.database;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationDatabase {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDatabase(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
        final long id = rs.getLong("id");
        final String name = rs.getString("name");
        final LocalDate date = rs.getDate("date").toLocalDate();
        final LocalTime time = rs.getTime("time").toLocalTime();
        return new Reservation(id, name, date, time);
    };

    public List<Reservation> findAll() {
        final String sql = """
                SELECT * FROM RESERVATION
                """;

        return jdbcTemplate.query(sql, reservationRowMapper);
    }
}
