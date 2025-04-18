package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomEscapeAdminController {
    @GetMapping("/admin")
    public String adminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservationAdminPage() {
        return "admin/reservation-legacy";
    }
}
