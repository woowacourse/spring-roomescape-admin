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
import roomescape.repository.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository repository;

    public ReservationController(ReservationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Reservation> readAll() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Reservation> add(@RequestBody CreateReservationRequest request) {
        Reservation savedReservation = repository.save(request);
        return ResponseEntity.ok()
            .header("Location", String.format("/reservations/%d", savedReservation.getId()))
            .body(savedReservation);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        repository.delete(id);
    }
}
