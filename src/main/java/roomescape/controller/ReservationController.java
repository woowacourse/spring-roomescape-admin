package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;

@RestController
public class ReservationController {

    @GetMapping("/reservations")
    public List<Reservation> findAll() {
        return new ArrayList<>();
    }
}
