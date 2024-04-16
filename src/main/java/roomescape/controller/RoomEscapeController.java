package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationSaveRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/admin")
public class RoomEscapeController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

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
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        var reservationDtos = reservations.stream()
                .map(Reservation::toDto)
                .toList();
        return ResponseEntity.ok(reservationDtos);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationSaveRequest request) {
        Reservation reservation = request.toReservation(index.getAndIncrement());
        reservations.add(reservation);
        return ResponseEntity.ok(reservation.toDto());
    }
}
