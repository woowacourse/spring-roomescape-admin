package roomescape.user.reservation.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.user.reservation.domain.Reservation;
import roomescape.user.reservation.domain.ReservationService;
import roomescape.user.reservation.domain.ReservationTime;
import roomescape.user.reservation.presentation.dto.ReservationV1Request;
import roomescape.user.reservation.presentation.dto.ReservationV1Response;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/reservations")
public class UserReservationV1RestController {

    private final ReservationService reservationService;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(final IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Void> handleIllegalStateException(final IllegalStateException e) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationV1Response>> retrieveReservations() {
        final List<Reservation> reservations = reservationService.findReservations();

        final List<ReservationV1Response> reservationV1Responses = reservations.stream()
                .map(reservation -> new ReservationV1Response(
                        reservation.getId(), reservation.getName(), reservation.getDate(), reservation.extractTime()))
                .toList();

        return ResponseEntity.ok(reservationV1Responses);
    }

    @PostMapping
    public ResponseEntity<ReservationV1Response> persistReservation(
            @RequestBody final ReservationV1Request reservationV1Request) {
        final Long savedReservationId = reservationService.saveReservation(new Reservation(
                null, reservationV1Request.name(), reservationV1Request.date(),
                new ReservationTime(null, reservationV1Request.time())));
        final Reservation foundReservation = reservationService.findReservation(savedReservationId);

        final ReservationV1Response reservationV1Response = new ReservationV1Response(
                foundReservation.getId(), foundReservation.getName(), foundReservation.getDate(), foundReservation.extractTime());

        return ResponseEntity.ok(reservationV1Response);
    }
}
