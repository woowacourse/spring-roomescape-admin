package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.domain.Reservation;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/admin")
    public String admin() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservations);
    }
}
