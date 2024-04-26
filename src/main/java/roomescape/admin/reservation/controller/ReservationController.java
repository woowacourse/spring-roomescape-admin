package roomescape.admin.reservation.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.admin.reservation.controller.dto.request.ReservationRequest;
import roomescape.admin.reservation.controller.dto.response.ReservationResponse;
import roomescape.admin.reservation.entity.Reservation;
import roomescape.admin.reservation.service.ReservationService;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAll() {
        List<ReservationResponse> reservationResponses = reservationService.findAll().stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationService.create(reservationRequest.toReservation());

        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        reservationService.delete(id);

        return ResponseEntity.ok().build();
    }
}
