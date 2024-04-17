package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/admin/reservation")
    public String getReservation() {
        return "admin/reservation-legacy";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation newReservation) {
        Reservation reservation = Reservation.toEntity(index.getAndIncrement(), newReservation);
        reservations.add(reservation);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation deleteReservation = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        reservations.remove(deleteReservation);
        return ResponseEntity.ok().build();
    }
}
