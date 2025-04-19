package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequest;
import roomescape.model.Reservation;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservationService.getReservations());
    }

    @PostMapping("")
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation newReservation = Reservation.toEntity(reservationService.getIndexAndIncrement(), reservationRequest);
        reservationService.addReservation(newReservation);
        return ResponseEntity.ok().body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable long id) {
        Reservation oldReservation = reservationService.deleteReservation(id);
        return ResponseEntity.ok().body(oldReservation);
    }
}
