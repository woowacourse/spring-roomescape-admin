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
    private final AtomicLong index = new AtomicLong(1);
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping()
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping()
    public ResponseEntity<Reservation> create(
            @RequestBody final Reservation reservation
    ) {
        final Reservation createdReservation = new Reservation(index.getAndIncrement(), reservation);
        reservations.add(createdReservation);
        return ResponseEntity.ok(createdReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") final long id
    ) {
        final Reservation target = reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR]"));

        reservations.remove(target);

        return ResponseEntity.ok().build();
    }
}
