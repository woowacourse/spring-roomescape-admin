package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.model.Reservation;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong atomicLong = new AtomicLong();

    @GetMapping("")
    public List<Reservation> reservations() {
        return reservations;
    }

    @PostMapping("")
    public Reservation addReservation(@RequestBody Reservation input) {
        Reservation reservation = Reservation.injectId(input, atomicLong.incrementAndGet());
        reservations.add(reservation);

        return reservation;
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable("id") Long id) {
        Reservation targetReservation = reservations.stream()
                .filter(reservation -> Objects.equals(reservation.id(), id))
                .findAny()
                .orElseThrow(RuntimeException::new);
        reservations.remove(targetReservation);
    }
}
