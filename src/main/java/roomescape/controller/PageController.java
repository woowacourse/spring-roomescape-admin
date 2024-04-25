package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("admin")
    public String getWelcomePage() {
        return "admin/index";
    }

    @GetMapping("reservation")
    public String getReservationPage() {
        return "admin/reservation";
    }

    @GetMapping("time")
    public String getSelectTimePage() {
        return "admin/time";
    }
}
