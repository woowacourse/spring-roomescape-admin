package roomescape.domain.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.service.ReservationService;
import roomescape.domain.dto.ReservationRequest;
import roomescape.domain.dto.ReservationResponse;

import java.util.List;

@RequestMapping("/reservations")
@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.ok(reservationService.save(reservationRequest));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.delete(id);

        return ResponseEntity.ok().build();
    }
}
