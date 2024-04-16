package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class RoomescapeController {

    private List<ReservationInfo> reservationInfos = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    @GetMapping("/admin")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationInfo> reservations() {
        return reservationInfos;
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ReservationInfo> addReservationInfo(@RequestBody ReservationInfo reservationInfo) {
        ReservationInfo newReservationInfo = ReservationInfo.toEntity(reservationInfo, counter.incrementAndGet());
        reservationInfos.add(newReservationInfo);
        return ResponseEntity.ok(newReservationInfo);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservationInfo(@PathVariable Long id) {
        reservationInfos.removeIf(reservationInfo -> reservationInfo.getId().equals(id));
        return ResponseEntity.ok().build();
    }
}
