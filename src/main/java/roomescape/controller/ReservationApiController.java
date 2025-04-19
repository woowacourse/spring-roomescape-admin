package roomescape.controller;

import java.time.Clock;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDateTime;
import roomescape.domain.ReservationName;
import roomescape.domain.Reservations;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {
    private final Reservations reservations;
    private final Clock clock;

    public ReservationApiController(final Reservations reservations, final Clock clock) {
        this.reservations = reservations;
        this.clock = clock;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> response = ReservationResponse.from(reservations.getReservations());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody final CreateReservationRequest createReservationRequest
    ) {
        ReservationName name = new ReservationName(createReservationRequest.name());
        ReservationDateTime dateTime = new ReservationDateTime(
                createReservationRequest.date(),
                createReservationRequest.time()
        );
        Reservation savedReservation = reservations.add(name, dateTime, clock);
        return ResponseEntity.ok(ReservationResponse.from(savedReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable final Long id) {
        reservations.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
