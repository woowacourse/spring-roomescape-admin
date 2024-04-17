package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoomescapeController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong();

    @GetMapping("admin")
    public String welcome() {
        return "admin/index";
    }

    @GetMapping("admin/reservation")
    public String getReservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("reservations")
    @ResponseBody
    public List<Reservation> findAll() {
        return reservations;
    }

    @PostMapping("reservations")
    @ResponseBody
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(index.incrementAndGet(), reservationRequest.getName(),
                reservationRequest.getDate(), reservationRequest.getTime());
        reservations.add(reservation);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Model model) {
        Reservation findReservation = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findAny()
                .orElseThrow();
        reservations.remove(findReservation);
        model.addAttribute(id);
        return ResponseEntity.ok().build();
    }
}
