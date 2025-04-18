package roomescape.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping({"", "/"})
    public String getAdminHomepage() {
        return "admin/index.html";
    }

    @GetMapping("/reservation")
    public String getAdminReservationPage() {
        return "admin/reservation-legacy.html";
    }
}
