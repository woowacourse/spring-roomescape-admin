package roomescape.domain.reservations.presentation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservations.entity.Reservation;
import roomescape.domain.reservations.infrastructure.ReservationJdbcTemplateRepository;
import roomescape.domain.reservations.presentation.dto.ReservationRequest;
import roomescape.domain.reservations.presentation.dto.ReservationResponse;

@RestController
public class ReservationController {

    private final ReservationJdbcTemplateRepository repository;

    public ReservationController(ReservationJdbcTemplateRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(
            @RequestBody ReservationRequest request
    ) {
        Reservation reservation = Reservation.of(
                null,
                request.name(),
                request.date(),
                request.time()
        );
        Reservation savedReservation = repository.save(reservation);
        return ResponseEntity.ok(ReservationResponse.from(savedReservation));
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        List<Reservation> reservations = repository.findAll();
        return ResponseEntity.ok(reservations).getBody();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id
    ) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
