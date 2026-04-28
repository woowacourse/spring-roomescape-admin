package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.util.DateAndTimeConverter;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.CreateReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

@RequestMapping("/reservations")
@RestController
public class ReservationController {
    private final Reservations reservations = new Reservations();

    @PostMapping
    public ResponseEntity<CreateReservationResponse> createReservation(@RequestBody CreateReservationRequest createReservationRequest) {
        Reservation reservation = new Reservation(
                createReservationRequest.name(),
                DateAndTimeConverter.parseToDate(createReservationRequest.date()),
                DateAndTimeConverter.parseToTime(createReservationRequest.time())
        );

        CreateReservationResponse createReservationResponse = new CreateReservationResponse(
                reservations.add(reservation),
                reservation.getName(),
                DateAndTimeConverter.formatDate(reservation.getDate()),
                DateAndTimeConverter.formatTime(reservation.getTime())
        );

        return ResponseEntity.ok(createReservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservations.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
