package roomescape.presentation.web.controller;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.ReservationService;
import roomescape.domain.Reservation;
import roomescape.presentation.web.request.CreateReservationWebRequest;
import roomescape.presentation.web.response.ReservationResponse;

@RestController
@RequestMapping("/reservations")
class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(@RequestBody @Valid CreateReservationWebRequest request) {
        Reservation newReservation = reservationService.reserve(request.toServiceRequest());
        ReservationResponse response = new ReservationResponse(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBy(@PathVariable Long id) {
        reservationService.deleteBy(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<Reservation> reservations = reservationService.findReservations();
        List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::new)
                .toList();

        return ResponseEntity.ok(responses);
    }
}
