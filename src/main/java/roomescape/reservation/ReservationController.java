package roomescape.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong atomicLong = new AtomicLong();
    private final JdbcTemplate jdbcTemplate;

    public ReservationController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        final String sql = "SELECT * FROM reservation";
        final List<Reservation> reservations = jdbcTemplate.query(sql, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        ));

        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(
                atomicLong.incrementAndGet(),
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time());

        reservations.add(reservation);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
        return ResponseEntity.ok().build();
    }
}
