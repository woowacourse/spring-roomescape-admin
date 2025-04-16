package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {

    private List<Reservation> reservations = List.of(
            new Reservation(1L, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)),
            new Reservation(2L, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0)),
            new Reservation(3L, "워니", LocalDate.of(2023, 1, 3), LocalTime.of(12, 0))
    );

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservations);
    }
}
