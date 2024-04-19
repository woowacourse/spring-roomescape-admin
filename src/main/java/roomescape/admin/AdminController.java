package roomescape.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.reservation.ReservationService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ReservationService reservationService;

    public AdminController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/reservation")
    public String reservation(Model model) {
        model.addAttribute("reservations", reservationService.findAllReservations());
        return "admin/reservation-legacy";
    }
}
