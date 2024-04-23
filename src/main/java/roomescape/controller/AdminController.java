package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String openAdminPage() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String openAdminReservationPage() {
        return "/admin/reservation";
    }

    @GetMapping("/admin/time")
    public String openAdminTimePage() {
        return "/admin/time";
    }
}
