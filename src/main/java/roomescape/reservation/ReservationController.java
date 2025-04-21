package roomescape.reservation;

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

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final Reservations reservations = new Reservations();
    private final ReservationDAO reservationDAO;

    public ReservationController(
            @Autowired final ReservationDAO reservationDAO
    ) {
        this.reservationDAO = reservationDAO;
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            @RequestBody final Reservation reservation
    ) {
        final Reservation savedReservation = reservationDAO.saveReservation(reservation);
        return ResponseEntity.ok(savedReservation);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readAllReservation() {
        return ResponseEntity.ok(reservationDAO.findAllReservation());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationById(
            @PathVariable("id") final long id
    ) {
        try {
            reservations.removeReservationById(id);
            return ResponseEntity.ok().build();
        } catch (final IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
