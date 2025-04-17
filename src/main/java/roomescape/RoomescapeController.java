package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RoomescapeController {
    @GetMapping("/admin")
    public String showAdminWelcome() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String showAllReservation() {
        return "admin/reservation-legacy";
    }
}
