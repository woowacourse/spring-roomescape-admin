package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequest;
import roomescape.reservation.Reservation;
import roomescape.reservation.Reservations;

import java.util.List;

@RestController
public class ReservationController {

    private final Reservations reservations;

    public ReservationController(Reservations reservations) {
        this.reservations = reservations;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> searchReservations() {
        List<Reservation> findReservations = reservations.findAll();
        return ResponseEntity.ok().body(findReservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toEntity();
        reservations.add(reservation);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservations.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
