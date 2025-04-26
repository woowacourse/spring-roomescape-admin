package roomescape.user.controller;

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
import roomescape.user.controller.dto.ReservationResponse;
import roomescape.user.domain.Reservation;
import roomescape.user.domain.ReservationTime;
import roomescape.user.repository.ReservationRepository;
import roomescape.user.repository.ReservationTimeRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class UserReservationRestController {

    private final ReservationRepository reservationRepository;
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
    public ResponseEntity<List<ReservationResponse>> retrieveReservations() {
        final List<Reservation> reservations = reservationRepository.findAll();
        final List<ReservationResponse> reservationRespons = reservations.stream()
                .map(reservation -> ReservationResponse.of(reservation,
                        reservationTimeRepository.findById(reservation.getTimeId())
                                .orElseThrow(() -> new IllegalStateException("Reservation time not found"))))
                .toList();

        return ResponseEntity.ok(reservationRespons);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> persistReservation(@RequestBody final Reservation reservation) {
        final Long id = reservationRepository.save(reservation);
        final Reservation foundReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Reservation not found"));
        final ReservationTime foundReservationTime = reservationTimeRepository.findById(foundReservation.getTimeId())
                .orElseThrow(() -> new IllegalStateException("Reservation time not found"));

        return ResponseEntity.ok(ReservationResponse.of(foundReservation, foundReservationTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable final Long id) {
        reservationRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
