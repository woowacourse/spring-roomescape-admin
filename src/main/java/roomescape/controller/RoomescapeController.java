package roomescape.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDto;
import roomescape.domain.ReservationStore;

@RestController
public class RoomescapeController {
    private final ReservationStore reservationStore = new ReservationStore();
    private AtomicLong index = new AtomicLong(0);

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDto reservationDto) {
        Reservation newReservation = Reservation.toEntity(index.incrementAndGet(), reservationDto);
        reservationStore.save(newReservation);

        return ResponseEntity.ok(newReservation);
    }

    @GetMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<Reservation> readReservations() {
        return reservationStore.getAll();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationStore.delete(id);

        return ResponseEntity.ok().build();
    }
}
