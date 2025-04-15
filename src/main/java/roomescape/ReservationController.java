package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "/admin/reservation-legacy.html";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> create(@RequestBody Map<String, String> reservationRequest) {
        Reservation reservation = new Reservation(index.getAndIncrement(), reservationRequest.get("name"),
                reservationRequest.get("date"), reservationRequest.get("time"));

        reservations.add(reservation);
        return ResponseEntity.ok().body(reservation);
    }
}
