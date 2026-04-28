package roomescape.reservation.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.domain.Reservation;

@RestController
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return reservations;
    }
}
