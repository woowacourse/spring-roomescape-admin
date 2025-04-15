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

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/admin")
    public String admin() {
        return "admin/index.html";
    }

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "admin/reservation-legacy.html";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        // 제거 필요
        reservations.add(new Reservation(1L, "moko", LocalDate.of(2025, 4, 15), LocalTime.of(17, 20)));
        return ResponseEntity.ok(reservations);
    }

    record Reservation(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
    ) {

    }
}
