package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Id;
import roomescape.model.Reservation;
import roomescape.model.Reservations;

@Controller
@RequestMapping("/reservations")
public class AdminReservationsController {

    private final Reservations reservations = new Reservations(new ArrayList<>());

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> readReservations() {
        List<ReservationResponse> responses = ReservationResponse.toResponses(reservations.findReservations());
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation newReservation = reservationRequest.toReservation(new Id());
        Reservation savedReservation = reservations.saveReservation(newReservation);
        return ResponseEntity.ok(savedReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservations.deleteReservationById(id);
        return ResponseEntity.ok().build();
    }
}
