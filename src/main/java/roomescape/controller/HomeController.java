package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    @GetMapping
    public String getWelcomePage() {
        return "redirect:/admin";
    }

    @GetMapping("/reservations/{id}")
    public String getReservationPage(@PathVariable final String id) {
        return "redirect:/admin/reservation";
    }

}
