package roomescape.user.controller;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.user.controller.dto.ReservationV1Request;
import roomescape.user.controller.dto.ReservationV1Response;
import roomescape.user.domain.Reservation;
import roomescape.user.domain.ReservationTime;
import roomescape.user.repository.ReservationRepository;
import roomescape.user.repository.ReservationTimeRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/reservations")
public class UserReservationV1RestController {

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
    public ResponseEntity<List<ReservationV1Response>> retrieveReservations() {
        final List<Reservation> reservations = reservationRepository.findAll();
        final List<ReservationV1Response> reservationV1Respons = reservations.stream()
                .map(reservation -> ReservationV1Response.of(reservation,
                        reservationTimeRepository.findById(reservation.getTimeId())
                                .orElseThrow(() -> new IllegalStateException("Reservation time not found"))))
                .toList();

        return ResponseEntity.ok(reservationV1Respons);
    }

    @PostMapping
    public ResponseEntity<Reservation> persistReservation(@RequestBody final ReservationV1Request reservationV1Request) {
        final ReservationTime reservationTime = new ReservationTime(null, reservationV1Request.time());
        final Long timeId = reservationTimeRepository.save(reservationTime);

        final Reservation reservation = new Reservation(null, reservationV1Request.name(), reservationV1Request.date(), timeId);
        final Long id = reservationRepository.save(reservation);
        final Optional<Reservation> found = reservationRepository.findById(id);

        return found.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
