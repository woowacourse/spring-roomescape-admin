package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/admin")
    public String admin(){
        return "index";
    }

    @GetMapping("/admin/reservation")
    public String adminReservation(){
        return "admin/reservation-legacy";
    }
}
