package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/index";  // templates/admin/index.html을 찾습니다.
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "admin/reservation";  // templates/admin/reservation.html을 찾습니다.
    }

    @GetMapping("/reservation-legacy")
    public String reservationLegacyPage() {
        return "admin/reservation-legacy";  // templates/admin/reservation-legacy.html을 찾습니다.
    }

    @GetMapping("/time")
    public String timePage() {
        return "admin/time";  // templates/admin/time.html을 찾습니다.
    }
}
