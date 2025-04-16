package roomescape;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private final Reservations reservations = new Reservations();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    List<Reservation> read() {
        return reservations.getReservations();
    }

    @PostMapping("/reservations")
    ResponseEntity<Reservation> write(@RequestBody Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/members/" + index.get())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        reservations.remove(id);
        return ResponseEntity.ok().build();
    }
}
