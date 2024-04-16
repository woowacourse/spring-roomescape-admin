package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class AdminController {
    private final List<Reservation> reservations = List.of(
            new Reservation(1, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)),
            new Reservation(2, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0)),
            new Reservation(3, "안나", LocalDate.of(2001, 1, 1), LocalTime.of(11, 9))
    );

    @GetMapping("/admin")
    public String getAdminPage() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String getReservationPage() {
        return "/admin/reservation-legacy";
    }

    @ResponseBody
    @GetMapping("/admin/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }
}
