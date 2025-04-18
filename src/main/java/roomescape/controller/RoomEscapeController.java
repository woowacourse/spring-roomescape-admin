package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomEscapeController {

    @GetMapping("/admin")
    public String getMainPage() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String getReservationPage() {
        return "/admin/reservation-legacy";
    }
}
