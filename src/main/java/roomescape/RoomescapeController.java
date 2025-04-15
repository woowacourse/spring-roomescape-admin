package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoomescapeController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong();

    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String getReservationPage(Model model) {
        model.addAttribute("reservations", reservations);
        return "admin/reservation-legacy";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }

    @ResponseBody
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest request) {
        final long reservationId = index.incrementAndGet();
        final Reservation reservation = new Reservation(reservationId, request.name(), request.date(), request.time());
        reservations.add(reservation);
        return ResponseEntity.ok(reservation);
    }
}
