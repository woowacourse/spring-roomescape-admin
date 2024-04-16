package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = List.of(
            new Reservation(1L, "브라운", LocalDate.now(), LocalTime.now()),
            new Reservation(2L, "브라운", LocalDate.now(), LocalTime.now()),
            new Reservation(3L, "브라운", LocalDate.now(), LocalTime.now())
    );

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservation() {
        return ResponseEntity.ok(reservations);
    }
}
