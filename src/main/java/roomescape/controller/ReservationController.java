package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.service.ReservationService;

@RequestMapping("/reservations")
@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping()
    public ResponseEntity<List<ReservationResponse>> readAllReservations() {
        List<ReservationResponse> reservations = reservationService.readAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping()
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation savedReservation = reservationService.saveReservation(reservationRequest.toEntity());
        return ResponseEntity.ok().body(ReservationResponse.from(savedReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable("id") long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
