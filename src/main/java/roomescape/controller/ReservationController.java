package roomescape.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservations.values().stream().toList());
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
