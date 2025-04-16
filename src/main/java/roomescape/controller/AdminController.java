package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin/reservation")
    public String reservation() {
        return "/admin/reservation-legacy";
    }
}
