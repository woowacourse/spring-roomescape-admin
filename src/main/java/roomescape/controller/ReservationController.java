package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return reservationService.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping
    public ReservationResponse createReservation(@RequestBody final ReservationCreateRequest request) {
        return ReservationResponse.from(
                reservationService.create(request.name(), request.date(), request.timeId())
        );
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable final long id) {
        reservationService.delete(id);
    }
}
