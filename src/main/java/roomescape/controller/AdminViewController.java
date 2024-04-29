package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminViewController {
    @GetMapping("/admin")
    public String showAdminMainPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String showAdminReservationPage() {
        return "admin/reservation";
    }

    @GetMapping("/admin/time")
    public String showAdminTimePage() {
        return "admin/time";
    }

    @GetMapping()
    public String showMainPage() {
        return "redirect:/admin";
    }

    @GetMapping("/reservation")
    public String showReservationPage() {
        return "redirect:/admin/reservation";
    }

    @GetMapping("/time")
    public String showTimePage() {
        return "redirect:/admin/time";
    }
}
