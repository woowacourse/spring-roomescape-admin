package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
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
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationResponse;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        return ResponseEntity.ok(reservations.stream().map(ReservationResponse::from).toList());
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationCreateRequest request) {
        Reservation reservation = new Reservation(
            index.getAndIncrement(),
            request.name(),
            request.date(),
            request.time()
        );

        reservations.add(reservation);
        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        Optional<Reservation> reservationToDelete = reservations.stream()
            .filter(reservation -> reservation.getId() == id)
            .findFirst();

        if (reservationToDelete.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        reservations.remove(reservationToDelete.get());
        return ResponseEntity.ok().build();
    }
}
