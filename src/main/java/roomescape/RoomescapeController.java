package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoomescapeController {

    private final Reservations reservations;
    private final AtomicLong index = new AtomicLong(1);

    public RoomescapeController() {
        this.reservations = new Reservations(List.of());
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

    @ResponseBody
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDto reservationDto) {
        Reservation reservation = new Reservation(index.getAndIncrement(), reservationDto.name(), reservationDto.date(),
                reservationDto.time());
        reservations.add(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

}
