package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminPageController {

    @GetMapping
    public String getAdmin() {
        return "redirect:/";
    }

    @GetMapping("reservation")
    public String getAdminReservation() {
        return "admin/reservation-legacy";
    }
}
