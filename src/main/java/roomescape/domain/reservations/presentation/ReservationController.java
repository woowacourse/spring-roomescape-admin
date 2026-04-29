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
import roomescape.domain.reservations.entity.ReservationTime;
import roomescape.domain.reservations.presentation.dto.ReservationRequest;
import roomescape.domain.reservations.presentation.dto.ReservationResponse;
import roomescape.domain.reservations.presentation.dto.ReservationTimeRequest;
import roomescape.domain.reservations.presentation.dto.ReservationTimeResponse;

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

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> saveTime(
            @RequestBody ReservationTimeRequest request
    ) {
        return ResponseEntity.ok(service.saveTime(request));
    }

    @GetMapping("/times")
    public List<ReservationTime> getTimes() {
        return ResponseEntity.ok(service.getTimes()).getBody();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(
            @PathVariable Long id
    ) {
        service.deleteTime(id);
        return ResponseEntity.ok().build();
    }
}
