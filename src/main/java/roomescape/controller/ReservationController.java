package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("reservations")
    public ResponseEntity<List<ReservationResponseDto>> reservations() {
        List<ReservationResponseDto> reservations = ReservationResponseDto.listOf(reservationService.getAllReservations());
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("reservations")
    public ResponseEntity<ReservationResponseDto> reserve(@RequestBody ReservationRequestDto reservationRequestDto) {
        ReservationResponseDto reservedReservation = reservationService.reserve(reservationRequestDto);
        URI reservationUri = URI.create("/reservations/" + reservedReservation.id());
        return ResponseEntity.created(reservationUri).body(reservedReservation);
    }

    @DeleteMapping("reservations/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
