package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public final class AdminController {

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservations() {
        return "admin/reservation-legacy";
    }
}
