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

    @GetMapping("reservation")
    public String showReservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok()
                             .body(reservations);
    }
}
