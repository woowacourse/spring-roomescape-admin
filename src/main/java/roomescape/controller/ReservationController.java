package roomescape.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/reservations")
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping()
    public List<Reservation> reservations() {
        Reservation reservation = new Reservation(1, "클로버", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0));
        reservations.add(reservation);
        return reservations;
    }
}
