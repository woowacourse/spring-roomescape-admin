package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @GetMapping
    public String viewWelcomePage() {
        return "admin/index";
    }

    @GetMapping("/reservation")
    public String viewReservationPage() {
        return "admin/reservation";
    }

    @GetMapping("/time")
    public String viewTimePage() {
        return "admin/time";
    }
}
