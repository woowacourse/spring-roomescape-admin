package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/admin")
public class AdminController {

    private static final String PATH_PREFIX = "admin/";

    @GetMapping("/")
    public String admin() {
        return PATH_PREFIX + "index";
    }

    @GetMapping("/reservation")
    public String adminReservation(){
        return PATH_PREFIX + "reservation-legacy";
    }
}
