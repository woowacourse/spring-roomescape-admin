package roomescape.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.model.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final AtomicLong autoIncrementId = new AtomicLong(0L);
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> reservations() {
        List<Reservation> findReservations = jdbcTemplate.query("SELECT id, name, date, time FROM reservation",
                (rs, rowNum) ->
                        new Reservation(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getDate("date").toLocalDate(),
                                rs.getTime("time").toLocalTime()));
        return ResponseEntity.ok(findReservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Long id = autoIncrementId.incrementAndGet();
        reservations.put(id, reservation.withId(id));
        return ResponseEntity.ok(reservations.get(id));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservations.remove(reservationId);
        return ResponseEntity.ok().build();
    }
}
