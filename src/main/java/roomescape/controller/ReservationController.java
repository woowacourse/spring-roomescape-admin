package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservationDatum() {
        List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::of)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservationData(@RequestBody ReservationRequest request) {
        Reservation reservation = request.toDomain(index.getAndIncrement());
        reservations.add(reservation);

        ReservationResponse response = ReservationResponse.of(reservation);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservationsData(@PathVariable long id) {
        boolean isRemoved = reservations.removeIf(reservation -> reservation.id().equals(id));
        if (isRemoved) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
