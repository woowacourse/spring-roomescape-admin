package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class RoomEscapeController {
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    public String getAdminPage() {
        return "/admin/index";
    }

    @GetMapping("/reservation")
    public String getAdminReservations(Model model) {
        var reservationDtos = reservations.stream()
                .map(Reservation::toDto)
                .toList();
        model.addAttribute("reservations", reservationDtos);
        return "/admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }
}
