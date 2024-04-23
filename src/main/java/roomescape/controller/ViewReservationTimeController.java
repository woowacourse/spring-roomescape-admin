package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewReservationTimeController {

    @GetMapping("/admin/time")
    public String reservationTimeAdminPage() {
        return "/admin/time";
    }
}
