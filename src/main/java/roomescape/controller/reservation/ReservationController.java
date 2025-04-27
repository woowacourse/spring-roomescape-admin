package roomescape.controller.reservation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dto.reservation.ReservationCreateRequest;
import roomescape.dto.reservation.ReservationResponse;
import roomescape.service.reservation.ReservationService;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationCreateRequest request) {
        return ResponseEntity.ok(reservationService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        reservationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
