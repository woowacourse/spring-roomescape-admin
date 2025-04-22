package roomescape.user.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.exception.DataNotFoundException;
import roomescape.user.domain.Reservation;
import roomescape.user.repository.reservation.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationRestController {

    private final ReservationRepository reservationRepository;

    public ReservationRestController(
            @Qualifier("h2ReservationRepository") final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(final IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Void> handleDataNotFoundException(final DataNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Reservation> persistReservation(
            @RequestBody final Reservation reservation
    ) {
        final Long id = reservationRepository.save(reservation);
        final Reservation found = reservationRepository.getOneById(id);
        return ResponseEntity.ok(found);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> retrieveReservations() {
        final List<Reservation> reservations = reservationRepository.findAll();

        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable final Long id) {
        final Reservation found = reservationRepository.getOneById(id);

        reservationRepository.delete(found);

        return ResponseEntity.ok().build();
    }
}
