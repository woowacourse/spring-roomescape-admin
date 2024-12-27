package roomescape.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AdminPageController {

    @GetMapping("/admin")
    public String adminHome() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String adminReservationPage() {
        return "/admin/reservation-new";
    }

    @GetMapping("/admin/time")
    public String adminTimePage() {
        return "/admin/time";
    }

    @GetMapping("/admin/theme")
    public String adminThemePage() {
        return "/admin/theme";
    }
}
