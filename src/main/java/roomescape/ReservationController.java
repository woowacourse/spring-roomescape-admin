package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> get() {
        return ResponseEntity.ok(reservations);
    }
}
