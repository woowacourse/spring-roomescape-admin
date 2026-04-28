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
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateRequest;
import roomescape.repository.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @PostMapping
    public ResponseEntity<Reservation> create(
            @RequestBody ReservationCreateRequest createRequest
    ) {
        Reservation createdReservation = reservationRepository.create(Reservation.create(
                createRequest.name(),
                createRequest.date()
        ), createRequest.timeId());

        return ResponseEntity.ok(createdReservation);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();

        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable long id
    ) {
        reservationRepository.delete(id);

        return ResponseEntity.ok().build();
    }
}
