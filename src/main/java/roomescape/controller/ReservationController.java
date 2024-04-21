package roomescape.controller;

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
import roomescape.application.service.ReservationService;
import roomescape.controller.request.CreateReservationWebRequest;
import roomescape.controller.response.ReservationWebResponse;
import roomescape.domain.Reservation;

@RestController
@RequestMapping("/reservations")
class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationWebResponse> reserve(@RequestBody @Valid CreateReservationWebRequest request) {
        Reservation newReservation = reservationService.reserve(request.toServiceRequest());
        ReservationWebResponse response = new ReservationWebResponse(newReservation);
        URI uri = URI.create("/reservations/" + newReservation.getId());

        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBy(@PathVariable Long id) {
        reservationService.deleteBy(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationWebResponse>> getReservations() {
        List<Reservation> reservations = reservationService.findReservations();
        List<ReservationWebResponse> responses = reservations.stream()
                .map(ReservationWebResponse::new)
                .toList();

        return ResponseEntity.ok(responses);
    }
}
