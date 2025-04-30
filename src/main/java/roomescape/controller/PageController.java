package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public final class PageController {
    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "admin/reservation";
    }

    @GetMapping
    public String welcomePage() {
        return "welcomePage";
    }

    @GetMapping("/admin/time")
    public String time() {
        return "admin/time";
    }
}
