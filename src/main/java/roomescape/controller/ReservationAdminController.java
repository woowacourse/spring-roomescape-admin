package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ReservationAdminController {

    @GetMapping("/")
    public String homePage() {
        return "admin/index";
    }

    @GetMapping("/reservation")
    public String manageReservations() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/time")
    public String manageTimes() {
        return "admin/time";
    }
}
