package roomescape.web.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.general.domain.Reservation;
import roomescape.general.dto.ReservationRequest;
import roomescape.general.dto.ReservationResponse;
import roomescape.general.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody final ReservationRequest reservationRequest
    ) {
        final Reservation createdReservation = reservationService.create(reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + createdReservation.getId()))
                .body(ReservationResponse.from(createdReservation));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        final List<Reservation> reservations = reservationService.findAll();
        return ResponseEntity.ok().body(reservations.stream()
                .map(ReservationResponse::from)
                .toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable final Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
