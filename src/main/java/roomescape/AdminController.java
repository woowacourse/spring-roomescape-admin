package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String homePage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String manageReservation() {
        return "admin/reservation-legacy";
    }
}
