package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String home() {
        return "/admin/index";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "/admin/reservation";
    }

    @GetMapping("/time")
    public String time() {
        return "/admin/time";
    }
}
