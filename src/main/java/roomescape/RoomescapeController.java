package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomescapeController {

    @GetMapping("/admin")
    public String homePage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String manageReservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> checkReservation() {
        return ResponseEntity.ok(Arrays.asList(
                new Reservation(1L, "brown",
                        LocalDate.of(2023, 1, 1),
                        LocalTime.of(23, 5)
                )));
    }


}
