package roomescape.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequest;
import roomescape.reservation.Reservation;
import roomescape.reservation.Reservations;

@RestController
@RequestMapping("/reservations")
public class ReservationAPIController {

    private final Reservations reservations = new Reservations();
    private final AtomicLong index = new AtomicLong();

    @GetMapping
    public ResponseEntity<List<Reservation>> searchReservations() {
        return ResponseEntity.ok().body(reservations.getAll());
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation newReservation = new Reservation(index.incrementAndGet(), reservationRequest.getName(),
                reservationRequest.getDate(), reservationRequest.getTime());
        reservations.add(newReservation);
        return ResponseEntity.ok().body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservations.removeById(id);
        return ResponseEntity.ok().build();
    }
}
