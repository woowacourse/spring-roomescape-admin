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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserRoomEscapeController {

    private static final AtomicLong autoIncrement = new AtomicLong(0);
    private static final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> addReservations(@RequestBody Reservation reservation) {
        Reservation newReservation = reservation.withId(autoIncrement.incrementAndGet());
        reservations.add(newReservation);
        return ResponseEntity.ok(newReservation);
    }

    public static void clear() {
        reservations.clear();
        autoIncrement.set(0);
    }
}
