package roomescape.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.request.ReservationCreateRequest;
import roomescape.controller.dto.response.ReservationResponse;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        reservationService.getReservations();
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @RequestBody ReservationCreateRequest request
    ) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{reservation-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("reservation-id") Long reservationId
    ) {
        return ResponseEntity.ok(null);
    }
}
