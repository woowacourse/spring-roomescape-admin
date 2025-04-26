package roomescape.user.reservation.presentation;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.user.reservation.domain.Reservation;
import roomescape.user.reservation.domain.ReservationService;
import roomescape.user.reservation.presentation.dto.ReservationResponse;
import roomescape.user.reservationtime.domain.ReservationTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class UserReservationRestController {

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
    public ResponseEntity<List<ReservationResponse>> retrieveReservations() {
        final Map<Reservation, ReservationTime> foundReservationsWithTimes = reservationService.findReservationsWithTimes();
        final List<ReservationResponse> reservationResponses = foundReservationsWithTimes.entrySet().stream()
                .map(foundReservationsWithTime -> ReservationResponse.of(
                        foundReservationsWithTime.getKey(), foundReservationsWithTime.getValue()))
                .toList();

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> persistReservation(@RequestBody final Reservation reservation) {
        final Long savedReservationId = reservationService.saveReservationWithTime(reservation);
        final Map.Entry<Reservation, ReservationTime> foundReservationWithTime = reservationService.findReservationWithTime(
                savedReservationId);

        return ResponseEntity.ok(ReservationResponse.of(foundReservationWithTime.getKey(), foundReservationWithTime.getValue()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable final Long id) {
        reservationService.deleteReservation(id);

        return ResponseEntity.ok().build();
    }
}
