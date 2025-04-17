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

    private final AtomicLong index = new AtomicLong(1L);
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest reservationRequest) {
        try {
            Reservation reservation = Reservation.of(reservationRequest, index.getAndIncrement());
            reservations.add(reservation);
            return ResponseEntity.ok(reservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservations(@PathVariable("id") long id) {
        boolean isDeleted = reservations.removeIf((reservation) -> reservation.getId() == id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
