package roomescape.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.model.Reservations;

@Controller
public class AdminReservationsController {

    private final AtomicLong id = new AtomicLong(1);
    private final Reservations reservations = new Reservations();

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> readReservations() {
        List<ReservationResponse> responses = ReservationResponse.toResponses(reservations.findReservations());
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservations.saveReservation(reservationRequest.toReservation(id.getAndIncrement()));
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservations.deleteReservationById(id);
        return ResponseEntity.ok().build();
    }
}
