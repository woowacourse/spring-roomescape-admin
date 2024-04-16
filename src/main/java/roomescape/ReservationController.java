package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Reservation>> reservations() {
        reservations.add(new Reservation(1L, "브라운", LocalDate.now(), LocalTime.now()));
        reservations.add(new Reservation(2L, "초코칩", LocalDate.now(), LocalTime.now()));
        reservations.add(new Reservation(3L, "뽀로로", LocalDate.now(), LocalTime.now()));

        return ResponseEntity.of(Optional.of(reservations));
    }
}
