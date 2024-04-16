package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    // TODO: 메서드 이름 정하기
    @GetMapping("/admin")
    public String readAdminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String readAdminReservationPage() {
        return "admin/reservation-legacy";
    }
}
