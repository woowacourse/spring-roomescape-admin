package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminPageController {

    @GetMapping
    public String getWelcomePage() {
        return "admin/index";
    }

    @GetMapping("reservation")
    public String getReservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("time")
    public String getSelectTimePage() {
        return "admin/time";
    }
}
