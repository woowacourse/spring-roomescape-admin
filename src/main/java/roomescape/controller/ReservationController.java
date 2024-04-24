package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.CreateReservationRequest;
import roomescape.controller.dto.CreateReservationResponse;
import roomescape.domain.Reservation;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readReservations() {
        List<Reservation> reservations = reservationService.readReservations();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<CreateReservationResponse> createReservation(@RequestBody CreateReservationRequest request) {
        CreateReservationResponse response = reservationService.createReservation(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteReservation(@PathVariable int id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
