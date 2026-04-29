package roomescape.step4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.step4.domain.Reservation;
import roomescape.step4.dto.ReservationRequest;
import roomescape.step4.dto.ReservationResponse;
import roomescape.step4.service.ReservationService;

import java.util.List;

@RestController("ReservationControllerStep4")
@RequestMapping("/step4/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAllReservations() {
        List<Reservation> reservations = reservationService.findAllReservations();
        List<ReservationResponse> response = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> saveReservation(@RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.saveReservation(request);
        ReservationResponse response = ReservationResponse.from(reservation);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);

        return ResponseEntity.ok().build();
    }
}
