package roomescape.reservation.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.entity.Reservation;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/admin/reservation")
    public String adminReservationDashboard() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationResponseDto>> readAllReservations() {
        List<ReservationResponseDto> allReservations = reservations.stream()
                .map(ReservationResponseDto::toDto)
                .toList();

        return ResponseEntity.ok(allReservations);
    }
}
