package roomescape;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {
    private final List<Reservation> reservations = new CopyOnWriteArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public List<Reservation> read() {
        return reservations;
    }

    @PostMapping("/reservations")
    public Reservation create(@RequestBody ReservationRequest request) {
        Long id = index.getAndIncrement();
        Reservation newReservation = request.toEntity(id);
        reservations.add(newReservation);
        return newReservation;
    }
}
