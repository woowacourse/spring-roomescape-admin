package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> reservations() {
        List<ReservationResponse> reservations = reservationService.findAll();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> save(@RequestBody ReservationRequest reservationRequest) {
        ReservationResponse reservationResponse = reservationService.save(reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + reservationResponse.id())).body(reservationResponse);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
