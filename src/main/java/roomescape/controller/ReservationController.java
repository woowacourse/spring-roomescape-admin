package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.Reservation;
import roomescape.ReservationRequest;
import roomescape.Reservations;

@Controller
public class ReservationController {

    private final Reservations reservations = new Reservations();

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> checkReservation() {
        return ResponseEntity.ok(reservations.getReservations());
    }

    @PostMapping("reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest request) {
        try {
            long id = reservations.nextId();
            Reservation reservation = request.toReservation(id);
            reservations.add(reservation);
            return ResponseEntity.ok(reservation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        boolean isRemoved = reservations.removeById(id);
        if (isRemoved) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
