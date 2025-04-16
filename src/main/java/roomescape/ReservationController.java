package roomescape;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private final Reservations reservations;
    private final AtomicLong index = new AtomicLong(1);

    public ReservationController() {
        this.reservations = new Reservations(List.of());
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return new ResponseEntity<>(reservations.getReservations(), HttpStatus.OK);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> createReservation(
            @RequestBody final ReservationRequestDto reservationRequestDto) {
        final Reservation reservation = reservationRequestDto.toEntity(index.getAndIncrement());
        reservations.add(reservation);
        return new ResponseEntity<>(ReservationResponseDto.from(reservation), HttpStatus.OK);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") final Long id) {
        reservations.deleteBy(id);
        return ResponseEntity.ok().build();
    }
}
