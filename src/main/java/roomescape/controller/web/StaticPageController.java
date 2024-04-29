package roomescape.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticPageController {
    @GetMapping(path = {"/", "/admin"})
    public String getAdminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String getReservationPage() {
        return "admin/reservation";
    }

    @GetMapping("/admin/time")
    public String getReservationTimePage() {
        return "admin/time";
    }
}
