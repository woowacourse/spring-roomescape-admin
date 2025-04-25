package roomescape.user.controller;

import java.util.List;
import java.util.Optional;
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
import roomescape.user.domain.ReservationTime;
import roomescape.user.repository.ReservationTimeRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/times")
public class UserReservationTimeRestController {

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
    public ResponseEntity<List<ReservationTime>> retrieveReservationTimes() {
        final List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        return ResponseEntity.ok(reservationTimes);
    }

    @PostMapping
    public ResponseEntity<ReservationTime> persistReservationTime(@RequestBody final ReservationTime reservationTime) {
        final Long id = reservationTimeRepository.save(reservationTime);
        final Optional<ReservationTime> found = reservationTimeRepository.findById(id);

        return found.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeReservationTime(@PathVariable final Long id) {
        reservationTimeRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
