package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoomescapeController {

    private final Reservations reservations;

    public RoomescapeController() {
        this.reservations = new Reservations(List.of(
                new Reservation(new AtomicLong(1), "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)),
                new Reservation(new AtomicLong(2), "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0))
        ));
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/index.html";
    }

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "admin/reservation-legacy.html";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return new ResponseEntity<>(reservations.getReservations(), HttpStatus.OK);
    }
}
