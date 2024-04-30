package roomescape.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String showAdminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String showReservationPage() {
        return "admin/reservation";
    }

    @GetMapping("/admin/time")
    public String showReservationTimePage() {
        return "admin/time";
    }
}
