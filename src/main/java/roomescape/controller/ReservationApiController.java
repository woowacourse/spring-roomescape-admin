package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public final class ReservationApiController {

    private final ReservationService reservationService;

    public ReservationApiController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponse> reservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping
    public ReservationResponse reserve(@RequestBody final ReservationRequest reservationRequest) {
        return reservationService.createReservation(reservationRequest);
    }

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable final Long id) {
        reservationService.deleteReservation(id);
    }
}
