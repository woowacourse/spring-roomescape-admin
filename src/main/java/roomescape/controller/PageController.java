package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping()
    String welcomePage() {
        return "welcomePage";
    }

    @GetMapping("/admin")
    String adminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    String adminReservationPage() {
        return "admin/reservation-legacy";
    }
}
