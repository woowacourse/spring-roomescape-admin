package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.request.ReservationRequest;
import roomescape.controller.response.ReservationResponse;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("reservations")
    public ResponseEntity<List<ReservationResponse>> reservations() {
        List<ReservationResponse> reservations = ReservationResponse.listOf(reservationService.getAllReservations());
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("reservations")
    public ResponseEntity<ReservationResponse> reserve(@RequestBody ReservationRequest reservationRequest) {
        ReservationResponse reservation = ReservationResponse.from(reservationService.reserve(reservationRequest));
        URI reservationUri = URI.create("/reservations/" + reservation.id());
        return ResponseEntity.created(reservationUri).body(reservation);
    }

    @DeleteMapping("reservations/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
