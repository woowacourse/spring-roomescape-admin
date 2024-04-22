package roomescape.admin.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/")
    public String welcomePage() {
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservationPage() {
        return "admin/reservation";
    }

    @GetMapping("/admin/time")
    public String timePage() {
        return "admin/time";
    }
}
