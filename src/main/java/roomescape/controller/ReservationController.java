package roomescape.controller;

import java.util.List;
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
import roomescape.repository.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @PostMapping
    public ReservationResponse saveReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationRepository.save(reservationRequest);
        return toResponse(reservation);
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(reservation.getId(),
                reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @GetMapping
    public List<ReservationResponse> findAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @DeleteMapping("/{reservationId}")
    public void delete(@PathVariable long reservationId) {
        reservationRepository.delete(reservationId);
    }
}
