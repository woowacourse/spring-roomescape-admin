package roomescape.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminRoomescapeController {

    @GetMapping
    public String getAdminPage() {
        return "admin/index";
    }

    @GetMapping("reservation")
    public String getAdminReservationPage() {
        return "admin/reservation-legacy";
    }
}
