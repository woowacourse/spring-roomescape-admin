package roomescape.controller.file;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReservationController {

    @GetMapping("/admin")
    public String getAdminHomepage() {
        return "admin/index.html";
    }

    @GetMapping("/admin/reservation")
    public String getAdminReservationPage() {
        return "admin/reservation-legacy.html";
    }
}
