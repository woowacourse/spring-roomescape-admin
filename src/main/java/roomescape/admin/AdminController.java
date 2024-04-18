package roomescape.admin;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationController;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ReservationController reservationController;

    public AdminController(ReservationController reservationController) {
        this.reservationController = reservationController;
    }

    @GetMapping
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/reservation")
    public String reservation(Model model) {
        ResponseEntity<List<Reservation>> reservations = reservationController.reservations();
        model.addAttribute("reservations", reservations.getBody());
        return "admin/reservation-legacy";
    }
}
