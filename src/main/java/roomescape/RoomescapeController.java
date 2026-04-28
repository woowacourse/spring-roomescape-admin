package roomescape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "/reservations")
public class RoomescapeController {

    private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong index = new AtomicLong(0);

    @PostMapping()
    public ResponseEntity<Reservation> createReservation(@RequestBody CreateReservationRequest request) {
        Reservation reservation = new Reservation(index.incrementAndGet(), request.name(), request.date(), request.time());
        reservations.add(reservation);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping()
    public List<Reservation> getReservations() {
        return reservations;
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        Reservation foundReservation = findReservation(reservationId);
        reservations.remove(foundReservation);
        return ResponseEntity.ok()
                .build();
    }

    private Reservation findReservation(Long reservationId) {
        return reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), reservationId))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
