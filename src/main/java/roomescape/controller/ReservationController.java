package roomescape.controller;

import java.util.List;
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
import roomescape.model.Reservations;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final Reservations reservations = new Reservations();

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return reservations.getReservations();
    }

    @PostMapping
    public ReservationResponse addReservation(@RequestBody ReservationRequest request) {
        return reservations.createReservation(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        try {
            reservations.deleteReservation(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
