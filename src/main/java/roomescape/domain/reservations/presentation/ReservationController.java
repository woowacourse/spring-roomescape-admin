package roomescape.domain.reservations.presentation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservations.application.ReservationService;
import roomescape.domain.reservations.entity.Reservation;
import roomescape.domain.reservations.presentation.dto.ReservationRequest;
import roomescape.domain.reservations.presentation.dto.ReservationResponse;

@RestController
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> saveReservation(
            @RequestBody ReservationRequest request
    ) {
        return ResponseEntity.ok(service.saveReservation(request));
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return ResponseEntity.ok(service.getReservations()).getBody();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id
    ) {
        service.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
