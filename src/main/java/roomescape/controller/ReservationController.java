package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationsResponse;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @PostMapping()
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest request) {
        Reservation newReservation = new Reservation(
                index.getAndIncrement(),
                request.getName(),
                request.getDate(),
                request.getTime()
        );
        reservations.add(newReservation);

        return ResponseEntity.ok(ReservationResponse.of(newReservation));
    }

    @GetMapping()
    public ResponseEntity<ReservationsResponse> read() {
        List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::of)
                .toList();
        return ResponseEntity.ok(ReservationsResponse.from(responses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);

            if (reservation.getId() == id) {
                reservations.remove(i);
                break;
            }
        }
        return ResponseEntity.ok().build();
    }
}
