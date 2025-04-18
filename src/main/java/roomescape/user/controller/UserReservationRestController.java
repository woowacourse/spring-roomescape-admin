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
import org.springframework.web.bind.annotation.RestController;
import roomescape.exception.DataNotFoundException;
import roomescape.user.domain.Reservation;
import roomescape.user.repository.ReservationRepository;

@RequiredArgsConstructor
@RestController
public class UserReservationRestController {

    private final ReservationRepository reservationRepository;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(final IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Void> handleDataNotFoundException(final DataNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> retrieveReservations() {
        final List<Reservation> reservations = reservationRepository.findAll();

        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> persistReservation(@RequestBody final Reservation reservation) {
        final Long id = reservationRepository.save(reservation);

        return reservationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable final Long id) {
        final Optional<Reservation> found = reservationRepository.findById(id);
        if (found.isEmpty()) {
            throw new DataNotFoundException("해당 예약 정보가 존재하지 않습니다. id = " + id);
        }

        reservationRepository.delete(found.get());

        return ResponseEntity.ok().build();
    }
}
