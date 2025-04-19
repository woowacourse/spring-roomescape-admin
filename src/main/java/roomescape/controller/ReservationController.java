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
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping()
    public ResponseEntity<List<ReservationResponse>> get() {
        return ResponseEntity.ok(reservationService.get());
    }

    @PostMapping()
    public ResponseEntity<ReservationResponse> create(
            @RequestBody final ReservationRequest reservationRequest) {
        return ResponseEntity.ok(reservationService.create(reservationRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
