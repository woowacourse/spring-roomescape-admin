package roomescape.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong index = new AtomicLong();

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        final List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::new)
                .toList();

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody final ReservationRequest reservationRequest
    ) {
        final Reservation reservation = new Reservation(
                index.incrementAndGet(),
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );
        reservations.add(reservation);

        return ResponseEntity.ok(new ReservationResponse(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") final Long id) {
        final Optional<Reservation> reservation = reservations.stream()
                .filter(value -> Objects.equals(value.getId(), id))
                .findFirst();
        if (reservation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reservations.remove(reservation.get());

        return ResponseEntity.ok().build();
    }
}
