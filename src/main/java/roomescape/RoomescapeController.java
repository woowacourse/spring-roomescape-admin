package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomescapeController {
    @GetMapping("/admin")
    String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    String reservation() {
        return "admin/reservation-legacy";
    }
}
