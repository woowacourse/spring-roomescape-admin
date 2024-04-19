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
import roomescape.dto.request.ReservationAddRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAllWithId()
                .entrySet()
                .stream()
                .map(e -> ReservationResponse.of(e.getKey(), e.getValue()))
                .toList();
    }

    @PostMapping
    public ReservationResponse addReservation(
            @RequestBody ReservationAddRequest reservationAddRequest) {
        Reservation reservation = reservationAddRequest.toReservation();
        Long id = reservationRepository.add(reservation);

        return ReservationResponse.of(id, reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable("id") Long id) {
        reservationRepository.remove(id);
    }
}
