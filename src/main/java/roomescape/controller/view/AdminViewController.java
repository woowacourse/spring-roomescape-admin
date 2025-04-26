package roomescape.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    @GetMapping
    public String home() {
        return "redirect:/admin/index.html";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "redirect:/admin/reservation.html";
    }

    @GetMapping("/time")
    public String time() {
        return "redirect:/admin/time.html";
    }
}
