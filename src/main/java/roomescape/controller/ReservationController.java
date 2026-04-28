package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;

@RestController
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> createReservation(@RequestBody Reservation reservation) {
        reservations.add(new Reservation(index.incrementAndGet(), reservation.name(), reservation.date(),
                reservation.time()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        reservations.removeIf(reservation -> Objects.equals(reservation.id(), id));
        return ResponseEntity.noContent().build();
    }
}
