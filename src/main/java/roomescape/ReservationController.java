package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @PostMapping
    public Reservation create(@RequestBody Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);
        return newReservation;
    }

    @GetMapping
    public List<Reservation> read() {
        return reservations;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
    }
}
