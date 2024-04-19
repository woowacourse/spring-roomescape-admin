package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {

    private final Reservations reservations = new Reservations();

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservation(Model model) {
        model.addAttribute("list", reservations.getReservations());
        return "admin/reservation-legacy";
    }
}
