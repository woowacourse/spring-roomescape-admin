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
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationCreateRequest request) {
        Reservation reservation = request.toEntity(index.getAndIncrement());

        reservations.add(reservation);
        ReservationResponse response = ReservationResponse.from(reservation);
        return ResponseEntity.ok(response);
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
