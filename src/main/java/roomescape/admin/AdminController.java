package roomescape.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/")
    public String welcomePage() {
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/index";
    }


    @GetMapping("/admin/reservation")
    public String reservationPage() {
        return "admin/reservation-legacy";
    }
}
