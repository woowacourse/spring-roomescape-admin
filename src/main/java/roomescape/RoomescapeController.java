package roomescape;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomescapeController {

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String getReservationPage(Model model) {
        model.addAttribute("reservations", reservations);
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }
}
