package roomescape.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String getHomepage() {
        return "index";
    }

    @GetMapping("/reservation")
    public String getReservationPage() {
        return "reservation-legacy";
    }
}
