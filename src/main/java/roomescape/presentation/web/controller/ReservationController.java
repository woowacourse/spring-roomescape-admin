package roomescape.presentation.web.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.ReservationService;
import roomescape.application.request.CreateReservationRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.presentation.web.response.ReservationResponse;
import roomescape.presentation.web.response.ReservationTimeResponse;

@RestController
@RequestMapping("/reservations")
class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(JdbcTemplate jdbcTemplate, ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(@RequestBody CreateReservationRequest request) {
        // create new Reservation
        Reservation newReservation = reservationService.reserve(request);

        // build web response dto
        ReservationResponse response = toDto(newReservation);
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

        // build web response dto
        List<ReservationResponse> responses = reservations.stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(responses);
    }

    private ReservationResponse toDto(Reservation newReservation) {
        ReservationTime time = newReservation.getTime();
        ReservationTimeResponse timeResponse = new ReservationTimeResponse(time.getId(), time.getStartAt());
        return new ReservationResponse(
                newReservation.getId(),
                newReservation.getName(),
                newReservation.getDate(),
                timeResponse
        );
    }
}
