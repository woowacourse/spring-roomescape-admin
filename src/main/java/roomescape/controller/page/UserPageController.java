package roomescape.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserPageController {

    @RequestMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/reservation-mine")
    public String myReservationPage() {
        return "reservation-mine";
    }
}
