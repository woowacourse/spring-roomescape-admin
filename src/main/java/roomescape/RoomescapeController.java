package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RoomescapeController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String adminReservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        Reservation newReservation = new Reservation(index.getAndIncrement(), reservation.getName(), reservation.getDate(),
                reservation.getTime());
        reservations.add(newReservation);
        return ResponseEntity.ok().body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable long id) {
        Reservation reservation1 = reservations.stream().filter(reservation -> reservation.getId() == id).findFirst()
                .orElseThrow(() ->new IllegalArgumentException("해당 ID 없음"));
        reservations.remove(reservation1);
        return ResponseEntity.ok().body(reservation1);
    }
}
