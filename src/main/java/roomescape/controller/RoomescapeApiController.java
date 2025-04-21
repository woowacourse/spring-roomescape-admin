package roomescape.controller;

import java.util.List;
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

@RestController
public class RoomescapeApiController {

    private final Reservations reservations;

    public RoomescapeApiController(final Reservations reservations) {
        this.reservations = reservations;
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
        Reservation reservation = request.toReservation();
        long savedId = reservations.addReservation(reservation);
        if (savedId > 0) {
            return ResponseEntity.ok(ReservationResponse.from(reservation.withId(savedId)));
        }
        return ResponseEntity.badRequest().build();

    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable long id) {
        boolean removed = reservations.removeReservationById(id);
        if (removed) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
