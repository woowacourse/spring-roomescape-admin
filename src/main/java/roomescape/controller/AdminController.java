package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "admin/reservation-legacy";
    }
}
