package roomescape.user.reservation.presentation;

import java.util.List;
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
import roomescape.user.reservation.domain.ReservationTime;
import roomescape.user.reservation.domain.ReservationTimeService;
import roomescape.user.reservation.presentation.dto.ReservationRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class UserReservationRestController {

    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(final IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Void> handleIllegalStateException(final IllegalStateException e) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> retrieveReservations() {
        return ResponseEntity.ok(reservationService.findReservations());
    }

    @PostMapping
    public ResponseEntity<Reservation> persistReservation(@RequestBody final ReservationRequest reservationRequest) {
        final ReservationTime foundReservationTime = reservationTimeService.findReservationTime(reservationRequest.timeId());

        final Long savedReservationId = reservationService.saveReservation(new Reservation(
                null, reservationRequest.name(), reservationRequest.date(), foundReservationTime));
        final Reservation foundReservation = reservationService.findReservation(savedReservationId);

        return ResponseEntity.ok(foundReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable final Long id) {
        reservationService.deleteReservation(id);

        return ResponseEntity.ok().build();
    }
}
