package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.CreateReservationRequest;
import roomescape.domain.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong id = new AtomicLong(0);

    @PostMapping
    public ResponseEntity<Reservation> reserve(@RequestBody CreateReservationRequest request) {
        Reservation newReservation = new Reservation(id.incrementAndGet(), request.name(), request.date(),
                request.time());
        reservations.add(newReservation);

        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBy(@PathVariable Long id) {
        Reservation found = reservations.stream()
                .filter(it -> it.hasSameId(id)).findFirst()
                .orElseThrow(RuntimeException::new);
        reservations.remove(found);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }
}
