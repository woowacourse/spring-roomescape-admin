package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminPageController {

    @GetMapping
    public String getAdminPage() {
        return "admin/index";
    }

    @GetMapping("/reservation")
    public String getReservation() {
        return "admin/reservation";
    }

    @GetMapping("/time")
    public String getTime() {
        return "admin/time";
    }
}
