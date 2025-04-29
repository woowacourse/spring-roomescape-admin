package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationTimeViewController {

    @GetMapping("/admin/time")
    public String getAdminReservationTimePage() {
        return "admin/time";
    }
}
