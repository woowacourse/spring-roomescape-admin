package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping
    public ResponseEntity<List<Reservation>> get() {
        return ResponseEntity.ok(new ArrayList<>(reservations));
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationCreateDto reservationCreateDto) {
        String name = reservationCreateDto.getName();
        String date = reservationCreateDto.getDate();
        String time = reservationCreateDto.getTime();

        reservations.add(new Reservation(index.incrementAndGet(), name, date, time));
        return ResponseEntity.ok(reservations.get(index.intValue()));
    }
}
