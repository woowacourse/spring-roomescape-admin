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

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservationService.findAllReservations());
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.ok().body(reservationService.create(reservationRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long reservationId) {
        reservationService.delete(reservationId);
        return ResponseEntity.ok().build();
    }
}
