package roomescape.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminPageController {

    @GetMapping
    public String showAdminPage() {
        return "index";
    }

    @GetMapping("/reservation")
    public String showReservationPage() {
        return "admin/reservation-legacy";
    }
}
