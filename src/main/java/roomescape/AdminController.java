package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AdminController {

    private final AtomicLong id = new AtomicLong(0L);
    private final List<Reservation> reservations = new ArrayList<>();

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
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation createdReservation = Reservation.of(id.incrementAndGet(), reservation);
        reservations.add(createdReservation);
        return ResponseEntity.ok(createdReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservations.removeIf(each -> Objects.equals(each.id(), id));
        return ResponseEntity.ok().build();
    }
}
