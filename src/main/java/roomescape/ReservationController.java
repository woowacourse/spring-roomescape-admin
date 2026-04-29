package roomescape;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservations")
    public List<Reservation> list() {
        return reservations;
    }

    @PostMapping("/reservations")
    public Reservation create(@RequestBody Reservation request) {



        Reservation reservation = new Reservation(
                index.incrementAndGet(),
                request.getName(),
                request.getDate(),
                request.getTime()
        );
        reservations.add(reservation);
        return reservation;
    }

    @DeleteMapping("/reservations/{id}")
    public void delete(@PathVariable Long id) {
        reservations.removeIf(r -> r.getId().equals(id));
    }

}
