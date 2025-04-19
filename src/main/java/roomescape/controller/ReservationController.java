package roomescape.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.Reservation;
import roomescape.Reservations;
import roomescape.controller.request.ReservationRequest;
import roomescape.controller.response.ReservationResponse;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final Reservations reservations = new Reservations();
    private final AtomicLong reservationIndex = new AtomicLong(1);

    @GetMapping
    ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok(reservations.getReservations());
    }

    @PostMapping
    ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toReservation(reservationIndex.getAndIncrement());
        reservations.add(reservation);
        return ResponseEntity.ok().body(ReservationResponse.of(reservation));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        reservations.remove(id);
        return ResponseEntity.ok().build();
    }
}
