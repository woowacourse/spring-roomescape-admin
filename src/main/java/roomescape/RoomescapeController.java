package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class RoomescapeController {

    @GetMapping
    public String welcome() {
        return "admin/index";
    }

    @GetMapping("reservation")
    public String getReservationPage() {
        return "admin/reservation-legacy";
    }

}
