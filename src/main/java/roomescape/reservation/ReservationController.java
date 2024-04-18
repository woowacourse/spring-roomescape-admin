package roomescape.reservation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {

    @GetMapping("/reservation")
    public String home() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/time")
    public String time() {
        return "admin/time";
    }
}
