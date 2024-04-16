package roomescape.controller;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateDto;
import roomescape.dto.ReservationDto;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    private AtomicLong index = new AtomicLong(0);

    private List<Reservation> reservations = new ArrayList<>();

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

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> getReservations() {
        List<ReservationDto> reservationDtos = reservations.stream().map(reservation -> ReservationDto.fromEntity(reservation)).toList();
        return ResponseEntity.ok(reservationDtos);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservations(@RequestBody ReservationCreateDto reservationCreateDto) {
        Reservation reservation = reservationCreateDto.toEntity(index.incrementAndGet());
        reservations.add(reservation);
        return ResponseEntity.ok(reservation);
    }
}
