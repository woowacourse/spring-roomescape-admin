package roomescape.reservation.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.controller.request.ReservationCreateRequest;
import roomescape.reservation.controller.response.ReservationResponse;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.Reservations;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

    private final Reservations reservations = new Reservations();
    private final AtomicLong reservationId = new AtomicLong();

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> reservationResponses = reservations.getReservations()
                .stream()
                .map(ReservationResponse::of)
                .toList();

        return ResponseEntity.ok(reservationResponses);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationCreateRequest request) {
        Reservation reservation = new Reservation(
                reservationId.incrementAndGet(),
                request.name(),
                LocalDateTime.of(LocalDate.parse(request.date()), LocalTime.parse(request.time()))
        );
        reservations.create(reservation);

        return ResponseEntity.ok(
                ReservationResponse.of(reservation)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Optional<Reservation> reservation = reservations.findById(id);

        if (reservation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        reservations.delete(reservation.get());
        return ResponseEntity.ok().build();
    }
}
