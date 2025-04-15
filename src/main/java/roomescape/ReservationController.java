package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> get() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(reservation, index.incrementAndGet());
        reservations.add(newReservation);
        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation selectedReservation = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        reservations.remove(selectedReservation);
        return ResponseEntity.ok().build();
    }
}
