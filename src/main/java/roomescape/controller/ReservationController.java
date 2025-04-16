package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.model.Reservation;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong atomicLong = new AtomicLong();

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> reservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(
            @RequestBody Reservation input
    ) {
        Reservation reservation = Reservation.injectId(input, atomicLong.incrementAndGet());
        reservations.add(reservation);

        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        Reservation targetReservation = reservations.stream()
                .filter(reservation -> Objects.equals(reservation.id(), id))
                .findAny()
                .orElseThrow(RuntimeException::new);
        reservations.remove(targetReservation);

        return ResponseEntity.ok().build();
    }
}
