package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("admin")
    public String welcome() {
        return "admin/index";
    }

    @GetMapping("reservation")
    public String reservation() {
        return "admin/reservation";
    }

    @GetMapping("admin/time")
    public String time() {
        return "admin/time";
    }

    @GetMapping("admin/time")
    public String time() {
        return "admin/time";
    }
}
