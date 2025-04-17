package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final String VIEW_PREFIX = "admin/";

    @GetMapping
    public String admin() {
        return VIEW_PREFIX + "index.html";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return VIEW_PREFIX + "reservation-legacy.html";
    }
}
