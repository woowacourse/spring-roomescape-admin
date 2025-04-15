package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomescapeController {
    private final List<Reservation> reservations;

    public RoomescapeController() {
        this.reservations = new ArrayList<>();
        reservations.addAll(List.of(
                new Reservation(1L, "브라운", LocalDate.of(2025, 4, 20), LocalTime.of(10, 0)),
                new Reservation(2L, "솔라", LocalDate.of(2025, 4, 20), LocalTime.of(11, 0)),
                new Reservation(3L, "부리", LocalDate.of(2025, 4, 21), LocalTime.of(10, 0))
        ));
    }

    @GetMapping("/admin")
    public String showAdminWelcome() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String showReservations() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getAllReservation() {
        return ResponseEntity.ok().body(reservations);
    }
}
