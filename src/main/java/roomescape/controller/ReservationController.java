package roomescape.controller;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import roomescape.domain.Reservation;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.service.ReservationFacade;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {

    private final ReservationFacade reservationFacade;
    private final ReservationService reservationService;

    public ReservationController(ReservationFacade reservationFacade, ReservationService reservationService) {
        this.reservationFacade = reservationFacade;
        this.reservationService = reservationService;
    }

    @ResponseBody
    @PostMapping("/reservations")
    public ResponseEntity<Object> create(@RequestBody ReservationRequest request) {
        try {
            Reservation reservation = reservationFacade.createReservation(request);
            return ResponseEntity.ok(ReservationCreateResponse.from(reservation));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> findAll() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
