package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong atomicLong = new AtomicLong();

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "./admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> searchReservations() {
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation){
        Reservation newReservation = Reservation.toEntity(reservation, atomicLong.incrementAndGet());
        reservations.add(newReservation);
        return ResponseEntity.ok().body(newReservation);
    }
}
