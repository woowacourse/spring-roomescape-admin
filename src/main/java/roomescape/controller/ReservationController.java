package roomescape.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.repository.ReservationRepository;

@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ReservationResponse create(@RequestBody ReservationRequest request) {
        Reservation reservation = reservationRepository.save(
                request.name(),
                request.date(),
                request.timeId()
        );

        return ReservationResponse.from(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        reservationRepository.deleteById(id);
    }
}
