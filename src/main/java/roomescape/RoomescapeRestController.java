package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomescapeRestController {
    private List<ReservationInfo> reservationInfos = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    @GetMapping("/reservations")
    public List<ReservationInfo> reservations() {
        return reservationInfos;
    }

    @PostMapping("/reservations")
    public ReservationInfo addReservationInfo(@RequestBody ReservationInfo reservationInfo) {
        ReservationInfo newReservationInfo = ReservationInfo.toEntity(reservationInfo, counter.incrementAndGet());
        reservationInfos.add(newReservationInfo);
        return newReservationInfo;
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservationInfo(@PathVariable Long id) {
        reservationInfos.removeIf(reservationInfo -> reservationInfo.getId().equals(id));
    }
}
