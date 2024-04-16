package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("admin/reservation")
    public String showReservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("admin/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        reservations.add(new Reservation(Long.valueOf(1), "방탈출", "2024-04-01", "10:00"));
        return ResponseEntity.ok()
                             .body(reservations);
    }
}
