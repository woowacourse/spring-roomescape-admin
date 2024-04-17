package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private AtomicLong index = new AtomicLong(1);
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/admin/reservation")
    public String reservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(index.getAndIncrement(), reservation);
        reservations.add(newReservation);
        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation reservationToDeleted = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(reservationToDeleted);

        return ResponseEntity.ok().build();
    }
}
