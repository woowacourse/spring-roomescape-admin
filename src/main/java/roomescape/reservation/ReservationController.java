package roomescape.reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final Map<Long, Reservation> reservations = new HashMap<>();
    private final AtomicLong atomicLong = new AtomicLong();

    @GetMapping
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservations.values()
                .stream()
                .toList()
        );
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequest reservationRequest) {
        long incrementId = atomicLong.incrementAndGet();
        Reservation reservation = new Reservation(
                incrementId,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time());

        reservations.put(incrementId, reservation);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        if (!reservations.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        reservations.remove(id);
        return ResponseEntity.ok().build();
    }
}
