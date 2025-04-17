package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomescapeController {
    private final List<Reservation> reservations;

    public RoomescapeController() {
        this.reservations = new ArrayList<>();
    }

    @GetMapping("/admin")
    public String showAdminWelcome() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String showAllReservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservation() {
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDto reservationDto) {
        Reservation newReservation = new Reservation(
                reservationDto.name(),
                reservationDto.date(),
                reservationDto.time());
        reservations.add(newReservation);
        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation findReservation = reservations.stream()
                .filter(reservation -> reservation.hasSame(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 id 입니다."));
        reservations.remove(findReservation);
        return ResponseEntity.ok().build();
    }
}
