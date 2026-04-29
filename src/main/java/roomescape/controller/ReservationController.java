package roomescape.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.request.ReservationRequest;
import roomescape.response.ReservationResponse;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return ReservationResponse.from(reservationService.findAllReservations());
    }

    @PostMapping
    public ReservationResponse registerReservation(@RequestBody ReservationRequest request) {
        ReservationTime reservationTime = reservationService.findTime(request.timeId());
        Reservation reservation = request.toReservation(reservationTime);
        return ReservationResponse.from(reservationService.addReservation(reservation));
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
    }
}
