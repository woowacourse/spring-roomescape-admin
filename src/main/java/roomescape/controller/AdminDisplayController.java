package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDisplayController {

    @GetMapping
    public String displayAdminMain() {
        return "admin/index";
    }

    @GetMapping("/reservation")
    public String displayAdminReservation() {
        return "admin/reservation-legacy";
    }
}
