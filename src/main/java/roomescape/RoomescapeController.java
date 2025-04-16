package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomescapeController {

    private final List<Reservation> reservations = new ArrayList<>(
            List.of(
                    new Reservation(1L, "홍길동", LocalDate.of(2023, 10, 1), LocalTime.of(10, 0)),
                    new Reservation(2L, "김철수", LocalDate.of(2023, 10, 2), LocalTime.of(11, 0))
            )
    );

    @GetMapping("/admin")
    public String getAdmin() {
        return "redirect:/";
    }

    @GetMapping("/admin/reservation")
    public String getAdminReservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }
}
