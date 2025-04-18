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
import roomescape.entity.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1L);

    @GetMapping
    public List<Reservation> readReservations() {
        return reservations;
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);
        return newReservation;
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
            .filter(r -> Objects.equals(r.getId(), id))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
        reservations.remove(reservation);
    }
}
