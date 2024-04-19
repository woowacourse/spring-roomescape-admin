package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoadPageController {
    @GetMapping("/admin")
    public String openAdminPage() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String openReservationPage() {
        return "/admin/reservation-legacy";
    }
}
