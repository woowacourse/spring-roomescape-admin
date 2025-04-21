package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationQueryController {

    @GetMapping("")
    public String home() {
        return "home/index";
    }

    @GetMapping("admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("admin/reservation")
    public String reservation() {
        return "admin/reservation-legacy";
    }
}
