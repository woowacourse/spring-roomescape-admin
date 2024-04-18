package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationSaveRequest;
import roomescape.service.ReservationService;

import java.util.List;

@Controller
public class AdminController {
    private final ReservationService reservationService;

    public AdminController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "/admin/index";
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        var reservationResponses = reservationService.getReservations()
                .stream()
                .map(Reservation::toDto)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationSaveRequest request) {
        var reservation = request.toReservation();
        var savedReservation = reservationService.createReservation(reservation);
        return ResponseEntity.ok(savedReservation.toDto());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
