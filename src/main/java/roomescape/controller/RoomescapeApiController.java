package roomescape.controller;

import java.util.List;
import java.util.Vector;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.CannotAddException;
import roomescape.exception.CannotRemoveException;

@RestController
public class RoomescapeApiController {

    private final Reservations reservations;

    public RoomescapeApiController() {
        this.reservations = new Reservations(new Vector<>());
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> findAllReservations() {
        return reservations.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        try {
            Reservation savedReservation = reservations.addReservation(request.toReservation());
            return ResponseEntity.ok(ReservationResponse.from(savedReservation));
        } catch (CannotAddException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable long id) {
        try {
            reservations.removeReservationById(id);
            return ResponseEntity.ok().build();
        } catch (CannotRemoveException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
