package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.model.Reservation;

@Controller
public class AdminController {

    private final AtomicLong index = new AtomicLong(1);

    private final List<Reservation> reservations = Arrays.asList(
            new Reservation(index.getAndIncrement(), "브라운", LocalDate.of(2024, 4, 1), LocalTime.parse("10:00")),
            new Reservation(index.getAndIncrement(), "솔라", LocalDate.of(2024, 4, 1), LocalTime.parse("11:00")),
            new Reservation(index.getAndIncrement(), "부리", LocalDate.of(2024, 4, 2), LocalTime.parse("14:00"))
    );

    @GetMapping("/admin")
    public String displayMain() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String displayAdminReservation() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> readReservations() {
        return ResponseEntity.ok().body(reservations);
    }
}
