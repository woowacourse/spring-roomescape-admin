package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.Reservation;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> readAll() {
        return List.copyOf(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        reservation.setId(index.incrementAndGet());
        reservations.add(reservation);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        Reservation foundReservation = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findAny()
                .orElseThrow();

        reservations.remove(foundReservation);
        return ResponseEntity.ok().build();
    }
}
