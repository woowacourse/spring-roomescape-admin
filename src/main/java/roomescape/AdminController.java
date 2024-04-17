package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/")
    public String enterWellComePage() {
        return "admin/index";
    }

    @GetMapping("/admin")
    public String enterAdminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String readReservations() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(1L, "브라운", LocalDate.of(2024, 4, 1), LocalTime.of(10, 0)));
        reservations.add(new Reservation(2L, "솔라", LocalDate.of(2024, 4, 1), LocalTime.of(11, 0)));
        reservations.add(new Reservation(3L, "부리", LocalDate.of(2024, 4, 2), LocalTime.of(14, 0)));
        return ResponseEntity.ok(reservations);
    }
}
