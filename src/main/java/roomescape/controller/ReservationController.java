package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.Reservation;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);
//    reservations.add(new Reservation(index.incrementAndGet(), "브라운", "2023-01-01", "10:00"));

    @RequestMapping("/reservations")
    public String reservations(Model model) {
        model.addAttribute("reservations", reservations);
        return "reservations";
    }
}
