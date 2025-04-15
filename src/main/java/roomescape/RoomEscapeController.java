package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RoomEscapeController {

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1L);

    @GetMapping("/admin")
    public String homePage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String manageReservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> checkReservation() {
        return ResponseEntity.ok(new ArrayList<>(reservations.values()));
    }

    @PostMapping("reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        LocalDate date = LocalDate.parse(request.get("date"));
        LocalTime time = LocalTime.parse(request.get("time"));
        Reservation reservation = new Reservation(index.getAndIncrement(), name, date, time);
        reservations.put(reservation.getId(), reservation);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        boolean existReservation = reservations.containsKey(id);
        if (existReservation) {
            reservations.remove(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
