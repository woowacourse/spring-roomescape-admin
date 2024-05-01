package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public String getAdminPage() {
        return "/admin/index";
    }

    @GetMapping("/reservation")
    public String getReservationPage() {
        return "/admin/reservation";
    }

    @GetMapping("/time")
    public String getTimePage() {
        return "/admin/time";
    }
}
