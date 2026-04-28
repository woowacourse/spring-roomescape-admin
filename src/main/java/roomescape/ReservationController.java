package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0L);

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(index.incrementAndGet(), reservation);
        reservations.add(newReservation);

        return new ResponseEntity<>(newReservation, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @DeleteMapping("/{reservation-id}")
    public ResponseEntity<Void> addReservation(@PathVariable("reservation-id") Long reservationId) {
        Reservation reservation = reservations.stream()
                .filter(findReservation -> Objects.equals(findReservation.getId(), reservationId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        reservations.remove(reservation);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
