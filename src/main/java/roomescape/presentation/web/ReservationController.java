package roomescape.presentation.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.business.service.ReservationService;
import roomescape.presentation.dto.request.ReservationCreateRequest;
import roomescape.presentation.dto.response.ReservationResponse;

import java.util.List;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getAll() {
        final List<ReservationResponse> response = reservationService.getAll();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> add(@RequestBody ReservationCreateRequest request) {
        final ReservationResponse response = reservationService.saveAndGet(request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> delete(@PathVariable("reservationId") long reservationId) {
        reservationService.deleteById(reservationId);

        return ResponseEntity.ok().build();
    }
}
