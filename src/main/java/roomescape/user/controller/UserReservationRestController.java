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
import roomescape.user.domain.Reservation;
import roomescape.user.repository.ReservationRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class UserReservationRestController {

    private final ReservationRepository reservationRepository;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(final IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> retrieveReservations() {
        final List<Reservation> reservations = reservationRepository.findAll();

        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> persistReservation(@RequestBody final Reservation reservation) {
        final Long id = reservationRepository.save(reservation);
        final Optional<Reservation> found = reservationRepository.findById(id);

        return found.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable final Long id) {
        final Optional<Reservation> found = reservationRepository.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        reservationRepository.delete(found.get());

        return ResponseEntity.ok().build();
    }
}
