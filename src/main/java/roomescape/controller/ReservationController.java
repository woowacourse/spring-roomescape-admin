package roomescape.controller;

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
import roomescape.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping
    public List<Reservation> reservations() {
        return reservations;
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable long id) {
        reservations.remove((int)id);
    }
}
