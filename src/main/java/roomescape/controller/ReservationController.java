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
import roomescape.controller.dto.CreateReservationRequest;
import roomescape.domain.Reservation;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Reservation> readAll() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<Reservation> add(@RequestBody CreateReservationRequest request) {
        Reservation savedReservation = service.save(request);
        return ResponseEntity.ok()
            .header("Location", String.format("/reservations/%d", savedReservation.getId()))
            .body(savedReservation);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        service.delete(id);
    }
}
