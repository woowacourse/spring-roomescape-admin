package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong id = new AtomicLong(0);

    @GetMapping
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> reserve(@RequestBody CreateReservationRequest request) {
        Reservation newReservation = new Reservation(
                id.incrementAndGet(),
                request.getName(),
                request.getDate(),
                request.getTime());
        reservations.add(newReservation);
        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation found = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        reservations.remove(found);

        return ResponseEntity.ok().build();
    }
}
