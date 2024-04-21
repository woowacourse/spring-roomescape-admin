package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong id = new AtomicLong(0);

    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(@RequestBody CreateReservationRequest request) {
        Reservation newReservation = new Reservation(
                id.incrementAndGet(),
                request.name(),
                request.date(),
                request.time());
        reservations.add(newReservation);

        return ResponseEntity.ok(
                new ReservationResponse(
                        id.get(),
                        newReservation.getName(),
                        newReservation.getDate(),
                        newReservation.getTime()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBy(@PathVariable Long id) {

        Optional<Reservation> foundReservation = reservations.stream()
                .filter(it -> it.hasSameId(id))
                .findFirst();

        if (foundReservation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        reservations.remove(foundReservation.get());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(it -> new ReservationResponse(
                        id.get(),
                        it.getName(),
                        it.getDate(),
                        it.getTime()))
                .toList();

        return ResponseEntity.ok(reservationResponses);
    }
}
