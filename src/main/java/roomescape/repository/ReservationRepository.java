package roomescape.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getAll() {
        return jdbcTemplate.query(
            "SELECT id, name, date, time FROM reservation",
            reservationRowMapper);
    }

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> new Reservation(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getDate("date").toLocalDate(),
        rs.getTime("time").toLocalTime()
    );
}
