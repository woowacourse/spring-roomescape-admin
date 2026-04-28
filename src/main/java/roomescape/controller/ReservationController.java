package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;

@RestController
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);
//    reservations.add(new Reservation(index.incrementAndGet(), "브라운", "2023-01-01", "10:00"));

    @GetMapping("/reservations")
    public  reservations(Model model) {
        model.addAttribute("reservations", reservations);
        return "reservations";
    }
}
