package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.repository.H2ReservationRepository;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final H2ReservationRepository repository;

    @Autowired
    public ReservationController(H2ReservationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ReservationResponse> findAllReservations() {
        return repository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping
    public ReservationResponse addReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toReservation();
        Long id = repository.save(reservation);
        return ReservationResponse.from(Reservation.of(reservation, id));
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
