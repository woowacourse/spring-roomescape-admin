package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    @GetMapping("admin")
    public String admin() {
        return "/admin/index";
    }

    @GetMapping("admin/reservation")
    public String reservation() {
        return "/admin/reservation-legacy";
    }

}
