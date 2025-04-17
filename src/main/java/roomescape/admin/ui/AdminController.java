package roomescape.admin.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping()
    public String getMainPage() {
        return "index";
    }

    @GetMapping("/reservation")
    public String getReservationPage() {
        return "reservation-legacy";
    }
}
