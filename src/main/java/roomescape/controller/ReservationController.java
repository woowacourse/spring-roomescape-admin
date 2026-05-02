package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.entity.Reservation;
import roomescape.domain.service.ReservationService;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> postReservation(
            @RequestBody @Valid ReservationRequestDto reservationRequestDto
    ) {
        return ResponseEntity.ok(reservationService.postReservation(reservationRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservationService.getAllReservation());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservationBy(id);
        return ResponseEntity.ok().build();
    }
}
