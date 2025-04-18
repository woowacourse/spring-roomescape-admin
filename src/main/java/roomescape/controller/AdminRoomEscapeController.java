package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminRoomEscapeController {

    @GetMapping("/admin")
    public String home() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "/admin/reservation-legacy";
    }
}
