package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private List<Reservation> reservations = List.of(
            new Reservation(1L, "브라운", LocalDate.now(), LocalTime.now()),
            new Reservation(2L, "초코칩", LocalDate.now(), LocalTime.now()),
            new Reservation(3L, "뽀로로", LocalDate.now(), LocalTime.now())
    );

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.of(Optional.of(reservations));
    }
}
