package roomescape.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public String readAdminPage() {
        return "admin/index";
    }

    @GetMapping("/reservation")
    public String readAdminReservationPage() {
        return "admin/reservation";
    }

    @GetMapping("/time")
    public String readTimePage() {
        return "admin/time";
    }
}
