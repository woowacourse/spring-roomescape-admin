package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    @GetMapping("/admin")
    public String homePage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String manageReservations() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/admin/time")
    public String manageTimes() {
        return "admin/time";
    }
}
