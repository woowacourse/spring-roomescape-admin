package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.service.ReservationService;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("reservations")
    public ResponseEntity<Set<ReservationResponseDto>> reservations() {
        Set<ReservationResponseDto> reservations = reservationService.getAllReservations()
                .stream()
                .map(Reservation::toResponseDto)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(reservations);
    }

    @PostMapping("reservations")
    public ResponseEntity<ReservationResponseDto> reserve(@RequestBody ReservationRequestDto reservationRequestDto) {
        return ResponseEntity.ok(reservationService.reserve(reservationRequestDto));
    }

    @DeleteMapping("reservations/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
