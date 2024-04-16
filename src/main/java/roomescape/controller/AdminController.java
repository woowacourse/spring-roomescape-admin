package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dto.ReservationResponseDto;
import roomescape.service.ReservationService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ReservationService reservationService;

    @Autowired
    public AdminController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public String home() {
        return "/admin/index";
    }

    @GetMapping("/reservation")
    public String reservation(final Model model) {
        final List<ReservationResponseDto> reservationResponseDtos = reservationService.getReservations();
        model.addAttribute("reservations", reservationResponseDtos);
        return "/admin/reservation-legacy";
    }
}
