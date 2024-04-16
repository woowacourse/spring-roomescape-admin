package roomescape.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String getMainPage() {
        return "/admin/index";
    }

    @GetMapping("/reservation")
    public String getReservationPage() {
        return "/admin/reservation-legacy";
    }
}
