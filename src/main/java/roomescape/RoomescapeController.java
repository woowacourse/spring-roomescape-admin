package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class RoomescapeController {
    private final List<Reservation> reservations;
    private final AtomicLong reservationIndex = new AtomicLong(1);

    public RoomescapeController() {
        this.reservations = new ArrayList<>();
    }

    @GetMapping("/admin")
    public String showAdminWelcome() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String showReservations() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getAllReservation() {
        return reservations;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDto reservationDto) {
        Reservation newReservation = new Reservation(
                reservationIndex.getAndIncrement(),
                reservationDto.name(),
                reservationDto.date(),
                reservationDto.time());
        reservations.add(newReservation);
        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        if (reservations.removeIf(reservation -> reservation.id().equals(id))) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
