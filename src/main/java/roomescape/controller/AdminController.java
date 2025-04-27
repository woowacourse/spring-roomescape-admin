package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String showAdminMainPage() {
        return "/admin/index";
    }

    @GetMapping("/time")
    public String showAdminTimePage() {
        return "/admin/time";
    }

    @GetMapping("/reservation")
    public String showReservationManagementPage() {
        return "/admin/reservation.html";
    }
}
