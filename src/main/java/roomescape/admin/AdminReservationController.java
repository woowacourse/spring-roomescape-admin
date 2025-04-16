package roomescape.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminReservationController {

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "admin/reservation-legacy";
    }
}
