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
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody final ReservationCreateRequest request) {
        final ReservationResponse response = reservationService.createReservation(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> reservationResponses = reservationService.getAllReservations()
                                                            .stream()
                                                            .map(ReservationResponse::new)
                                                            .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable final Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
