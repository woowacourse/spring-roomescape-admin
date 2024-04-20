package roomescape.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.model.Reservation;
import roomescape.model.Reservations;
import roomescape.dto.ReservationCreateRequestDto;

@RestController
public class ReservationController {

    private final Reservations reservations;
    private final AtomicLong index = new AtomicLong(0);

    public ReservationController() {
        this.reservations = new Reservations();
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservations.getReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationCreateRequestDto reservationCreateRequestDto) {
        Reservation reservation = reservationCreateRequestDto.createReservation(index.incrementAndGet());
        reservations.add(reservation);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reservations.delete(id);
        return ResponseEntity.ok().build();
    }
}
