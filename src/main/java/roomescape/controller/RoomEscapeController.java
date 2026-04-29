package roomescape.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.repository.ReservationRepository;
import roomescape.request.ReservationRequest;
import roomescape.response.ReservationResponse;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class RoomEscapeController {
    private final ReservationRepository reservationRepository;

    public RoomEscapeController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return ReservationResponse.from(reservationRepository.findAllReservations());
    }

    @PostMapping
    public ReservationResponse registerReservation(@RequestBody ReservationRequest request) {
        return reservationRepository.addReservation(request);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteById(id);
    }
}
