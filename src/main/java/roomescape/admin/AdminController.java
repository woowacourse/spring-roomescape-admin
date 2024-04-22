package roomescape.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.reservation.service.ReservationService;
import roomescape.reservation.service.TimeSlotService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ReservationService reservationService;
    private final TimeSlotService timeSlotService;

    public AdminController(final ReservationService reservationService, final TimeSlotService timeSlotService) {
        this.reservationService = reservationService;
        this.timeSlotService = timeSlotService;
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

    @GetMapping("/time")
    public String time(Model model) {
        model.addAttribute("timeSlots", timeSlotService.findAll());
        return "admin/time";
    }
}
