package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public List<Reservation> read() {
        return reservations;
    }
}
