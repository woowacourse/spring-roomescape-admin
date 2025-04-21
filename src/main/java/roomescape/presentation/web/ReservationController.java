package roomescape.presentation.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.business.domain.Reservation;
import roomescape.business.service.ReservationService;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

import java.util.List;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getAll() {
        final List<Reservation> reservations = reservationService.getAll();
        final List<ReservationResponse> response = ReservationResponse.fromList(reservations);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> add(@RequestBody ReservationCreateRequest request) {
        final Reservation reservation = reservationService.saveAndGet(request);
        final ReservationResponse response = ReservationResponse.from(reservation);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> delete(@PathVariable("reservationId") long reservationId) {
        reservationService.deleteById(reservationId);

        return ResponseEntity.ok().build();
    }
}
