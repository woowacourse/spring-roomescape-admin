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
import roomescape.user.reservation.domain.ReservationTime;
import roomescape.user.reservation.domain.ReservationTimeService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/times")
public class UserReservationTimeRestController {

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
    public ResponseEntity<List<ReservationTime>> retrieveReservationTimes() {
        final List<ReservationTime> reservationTimes = reservationTimeService.findAllReservationTimes();

        return ResponseEntity.ok(reservationTimes);
    }

    @PostMapping
    public ResponseEntity<ReservationTime> persistReservationTime(@RequestBody final ReservationTime reservationTime) {
        final Long id = reservationTimeService.saveReservationTime(reservationTime);
        final ReservationTime foundReservationTime = reservationTimeService.findReservationTime(id);

        return ResponseEntity.ok(foundReservationTime);
    }

    @DeleteMapping("/{id}")  // TODO. 이미 존재하는 Reservaiton에 대한 고려
    public ResponseEntity<Void> removeReservationTime(@PathVariable final Long id) {
        reservationTimeService.deleteReservationTime(id);

        return ResponseEntity.ok().build();
    }
}
