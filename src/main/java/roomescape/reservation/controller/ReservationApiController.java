package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.service.ReservationService;

import java.util.List;

@RestController
public class ReservationApiController {

    private final ReservationService reservationService;

    private ReservationApiController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getAll() {
        List<ReservationResponse> reservationResponses = reservationService.getReservations();
        return ResponseEntity.ok().body(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> create(@RequestBody final ReservationRequest reservationRequest) {
        final ReservationResponse newReservationResponse = reservationService.saveReservation(reservationRequest);
        return ResponseEntity.ok().body(newReservationResponse);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        reservationService.deleteReservationTime(id);
        return ResponseEntity.noContent().build();
    }
}
