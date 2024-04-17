package roomescape.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong atomicLong = new AtomicLong();

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(
                atomicLong.incrementAndGet(),
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time());

        reservations.add(reservation);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
        return ResponseEntity.ok().build();
    }
}
