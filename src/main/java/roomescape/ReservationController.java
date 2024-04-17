package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong id = new AtomicLong(0);

    @PostMapping
    public ResponseEntity<Reservation> reserve(@RequestBody CreateReservationRequest request) {
        Reservation newReservation = new Reservation(
                id.incrementAndGet(),
                request.name(),
                request.date(),
                request.time());
        reservations.add(newReservation);
        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBy(@PathVariable Long id) {
        Reservation found = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        reservations.remove(found);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }
}
