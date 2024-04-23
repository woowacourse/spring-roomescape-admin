package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminViewController {
    @GetMapping()
    public String showAdminMainPage() {
        return "admin/index";
    }

    @GetMapping("/reservation")
    public String showAdminReservationPage() {
        return "admin/reservation-legacy";
    }
}
