package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.service.ReservationService;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<Reservation> getReservations() {
        return reservationService.selectReservations();
    }

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public Reservation createReservation(@RequestBody ReservationRequest reservationRequest) {
        return reservationService.createReservation(reservationRequest);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReservation(@PathVariable long id) {
        reservationService.deleteReservation(id);
    }
}
