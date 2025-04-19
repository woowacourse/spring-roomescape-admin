package roomescape.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationPageController {

    @GetMapping("/admin/reservation")
    public String reservation(
    ) {
        return "/admin/reservation-legacy.html";
    }
}
