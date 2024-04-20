package roomescape.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.business.ReservationService;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationSaveRequest;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        return ResponseEntity.ok(reservationService.getReservations());
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody ReservationSaveRequest request) {
        var reservation = request.toModel();
        var reservationId = reservationService.createReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservationId)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
