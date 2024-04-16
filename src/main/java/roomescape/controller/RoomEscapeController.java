package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.domain.Reservation;

@Controller
public class RoomEscapeController {
    private List<Reservation> reservations = List.of(
            new Reservation(1, "test", "2024-04-16", "10:00")
    );

    @GetMapping("/admin")
    public String openAdminPage() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String openReservationPage() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readReservations() {
        return ResponseEntity.ok(reservations);
    }

}
