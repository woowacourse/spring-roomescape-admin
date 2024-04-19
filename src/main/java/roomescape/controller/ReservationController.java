package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRequestDto;
import roomescape.domain.ReservationResponseDto;
import roomescape.service.AdminService;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ReservationController {
    private final AdminService adminService;

    public ReservationController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("reservations")
    public ResponseEntity<Set<ReservationResponseDto>> reservations() {
        Set<ReservationResponseDto> reservations = adminService.getAllReservations()
                .stream()
                .map(Reservation::toResponseDto)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(reservations);
    }

    @PostMapping("reservations")
    public ResponseEntity<ReservationResponseDto> reserve(@RequestBody ReservationRequestDto reservationRequestDto) {
        return ResponseEntity.ok(adminService.reserve(reservationRequestDto));
    }

    @DeleteMapping("reservations/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        adminService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
