package roomescape.reservation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final Reservations reservations = new Reservations();

    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            @RequestBody final Reservation reservation
    ) {
        final Reservation savedReservation = reservations.saveReservation(reservation);
        return ResponseEntity.ok(savedReservation);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readAllReservation() {
        return ResponseEntity.ok(reservations.getReservations());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationById(
            @PathVariable("id") final long id
    ) {
        reservations.removeReservation(id);

        return ResponseEntity.ok().build();
    }
}
