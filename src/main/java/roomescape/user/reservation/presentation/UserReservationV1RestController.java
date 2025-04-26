package roomescape.user.reservation.presentation;

import java.util.List;
import java.util.Map;
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
import roomescape.user.reservation.presentation.dto.ReservationV1Request;
import roomescape.user.reservation.presentation.dto.ReservationV1Response;
import roomescape.user.reservationtime.domain.ReservationTime;
import roomescape.user.reservationtime.domain.ReservationTimeRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/reservations")
public class UserReservationV1RestController {

    private final ReservationService reservationService;
    private final ReservationTimeRepository reservationTimeRepository;

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
        final Map<Reservation, ReservationTime> reservationsWithTimes = reservationService.findReservationsWithTimes();

        final List<ReservationV1Response> reservationV1Responses = reservationsWithTimes.entrySet().stream()
                .map(entry -> ReservationV1Response.of(entry.getKey(), entry.getValue()))
                .toList();

        return ResponseEntity.ok(reservationV1Responses);
    }

    @PostMapping
    public ResponseEntity<ReservationV1Response> persistReservation(
            @RequestBody final ReservationV1Request reservationV1Request) {

        final ReservationTime reservationTime = new ReservationTime(null, reservationV1Request.time());
        final Long savedReservationTimeId = reservationTimeRepository.save(reservationTime);

        final Reservation reservation = new Reservation(
                null, reservationV1Request.name(), reservationV1Request.date(), savedReservationTimeId);
        final Long savedReservationId = reservationService.saveReservationWithTime(reservation);

        final Map.Entry<Reservation, ReservationTime> reservationWithTime = reservationService.findReservationWithTime(
                savedReservationId);

        return ResponseEntity.ok(ReservationV1Response.of(reservationWithTime.getKey(), reservationWithTime.getValue()));
    }
}
