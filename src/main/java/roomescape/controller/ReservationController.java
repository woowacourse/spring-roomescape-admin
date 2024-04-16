package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>(List.of(
            new Reservation(1L, "브라운", LocalDateTime.of(2023, 1, 1, 10, 0)),
            new Reservation(2L, "브라운", LocalDateTime.of(2023, 1, 2, 11, 0))));

    @GetMapping("/admin")
    public String getAdminPage() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String getReservationPage(Model model) {
        List<ReservationDto> reservationDtos = reservations.stream().map(reservation -> ReservationDto.fromEntity(reservation)).toList();
        model.addAttribute("reservationDtos", reservationDtos);
        return "/admin/reservation-legacy";
    }

    @GetMapping("/admin/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationDto>> getReservations() {
        List<ReservationDto> reservationDtos = reservations.stream().map(reservation -> ReservationDto.fromEntity(reservation)).toList();
        return ResponseEntity.ok(reservationDtos);
    }
}
