package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationApiController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong reservationId = new AtomicLong();

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Map<String, String> newReservation) {

        LocalDate date = LocalDate.parse(newReservation.get("date"));
        String name = newReservation.get("name");
        LocalTime time = LocalTime.parse(newReservation.get("time"));

        Reservation created = new Reservation(reservationId.incrementAndGet(), name, date, time);
        reservations.add(created);

        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Optional<Reservation> find = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst();

        if (find.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        reservations.remove(find.get());
        return ResponseEntity.ok().build();
    }
}
