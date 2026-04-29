package roomescape.repository;

import java.util.List;
import java.util.NoSuchElementException;
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

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) ->
        new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            rs.getTime("time").toLocalTime()
        );

    public void save(Reservation reservation) {
        jdbcTemplate.update(
            "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime());
    }

    public void deleteById(long reservationId) {
        int rowCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM reservation WHERE id = ?",
            Integer.class,
            reservationId
        );
        if (rowCount == 0) {
            throw new NoSuchElementException("존재하지 않는 예약 아이디 입니다. reservationId: " + reservationId);
        }

        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", reservationId);
    }
}
