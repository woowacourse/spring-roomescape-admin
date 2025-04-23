package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.service.ReservationService;

import java.util.List;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/admin/reservation")
    public String adminReservationDashboard() {
        return "admin/reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> readAllReservations() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> add(@RequestBody ReservationRequestDto requestDto) {
        return ResponseEntity.ok(reservationService.add(requestDto));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
