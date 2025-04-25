package roomescape.controller.reservation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.reservation.request.ReservationRequest;
import roomescape.controller.reservation.response.ReservationResponse;
import roomescape.service.ReservationService;

@RequestMapping("/reservations")
@RestController
public final class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> save(@RequestBody ReservationRequest reservationRequest) {
        final ReservationResponse reservationResponse = reservationService.save(reservationRequest);
        return ResponseEntity.ok(reservationResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> read() {
        final List<ReservationResponse> reservationResponses = reservationService.read();
        return ResponseEntity.ok(reservationResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.ok().build();
    }


}
