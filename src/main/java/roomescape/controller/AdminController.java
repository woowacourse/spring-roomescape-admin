package roomescape.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.service.AdminService;

@Controller
public class AdminController {

    private final AdminService adminService;

    public AdminController() {
        this.adminService = new AdminService();
    }

    @GetMapping("admin")
    public String welcome() {
        return "admin/index";
    }

    @GetMapping("admin/reservation")
    public String reservation(Model model) {
        model.addAttribute("reservations", adminService.getAllReservations());
        return "admin/reservation-legacy";
    }

    @GetMapping("reservations")
    @ResponseBody
    public List<Reservation> reservations() {
        return adminService.getAllReservations();
    }
}
