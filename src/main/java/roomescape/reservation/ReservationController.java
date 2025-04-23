package roomescape.reservation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(
            @Autowired final ReservationService reservationService
    ) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody final ReservationRequest request
    ) {
        final ReservationResponse response = reservationService.createReservation(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> readAllReservation() {
        return ResponseEntity.ok(reservationService.findAllReservation());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationById(
            @PathVariable("id") final long id
    ) {
        try {
            reservationService.deleteReservationById(id);
            return ResponseEntity.ok().build();
        } catch (final IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
