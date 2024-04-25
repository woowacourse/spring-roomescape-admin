package roomescape.reservationtime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationTimeController {

    @GetMapping("/admin/time")
    String time() {
        return "/admin/time";
    }
}