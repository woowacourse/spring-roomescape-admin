package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomescapeController {

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
        return new ResponseEntity<>(reservations.getReservations(), HttpStatus.OK);
    }
}
