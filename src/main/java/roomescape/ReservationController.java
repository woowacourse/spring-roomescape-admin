package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {

    private final AtomicLong counter = new AtomicLong();
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        // TODO: 직접 추가한 값 제거하기
        reservations.add(
                new Reservation(counter.incrementAndGet(), "Dora", LocalDate.now(), LocalTime.now()));
        reservations.add(
                new Reservation(counter.incrementAndGet(), "Mark", LocalDate.now(), LocalTime.now()));
        return ResponseEntity.ok().body(reservations);
    }
}
