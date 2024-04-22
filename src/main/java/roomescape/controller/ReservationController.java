package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.request.ReservationRequest;
import roomescape.model.Reservation;
import roomescape.service.ReservationService;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservationService.getReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest request) {
        Reservation reservation = new Reservation(request.name(), request.date(), request.time());
        long id = reservationService.addReservations(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable("id") long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
