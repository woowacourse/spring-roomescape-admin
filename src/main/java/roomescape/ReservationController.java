package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicInteger id = new AtomicInteger(1);

    @GetMapping("/reservations")
    public List<Reservation> reservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    public Reservation reserve(@RequestBody ReservationRequestDto reservationRequestDto) {
        Reservation reservation = reservationRequestDto.toEntity(id.getAndIncrement());
        reservations.add(reservation);
        return reservation;
    }

    @DeleteMapping("/reservations/{id}")
    public void cancel(@PathVariable int id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
    }
}
