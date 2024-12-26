package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AdminPageController {

    @GetMapping("/admin")
    public String adminHome() {
        return "/admin/index";
    }

    @GetMapping("/reservation")
    public String adminReservationPage() {
        return "/admin/reservation";
    }

    @GetMapping("/admin/time")
    public String adminTimePage() {
        return "/admin/time";
    }
}
