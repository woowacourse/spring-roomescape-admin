package roomescape;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public List<Reservation> reservations() {
        return reservations;
    }
}
