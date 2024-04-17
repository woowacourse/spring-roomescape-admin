package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong reservationIndex = new AtomicLong(0);

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public Reservation reserve(@RequestBody ReservationDto reservationDto) {
        Reservation reservation = new Reservation(reservationIndex.incrementAndGet(), reservationDto.getName(), reservationDto.getDate(), reservationDto.getTime());
        reservations.add(reservation);
        return reservation;
    }
}

