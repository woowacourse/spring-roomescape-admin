package roomescape;

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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoomescapeController {

    private AtomicLong index = new AtomicLong(0);
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String adminReservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationDto> reservations() {
        return reservations.stream()
                .map(ReservationDto::from)
                .toList();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ReservationDto> addReservation(@RequestBody ReservationDto reservationDto) {
        Reservation reservation = new Reservation(
                index.incrementAndGet(),
                reservationDto.getName(),
                reservationDto.getDate(),
                reservationDto.getTime()
        );
        reservations.add(reservation);
        return ResponseEntity.ok(
                new ReservationDto(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime()
                )
        );
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") long id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
        return ResponseEntity.ok().build();
    }
}
