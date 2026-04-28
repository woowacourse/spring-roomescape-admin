package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong idSequence = new AtomicLong(0);

    @PostMapping("/reservations")
    public Reservation createReservation(@RequestBody ReservationRequest request) {
        final Reservation reservation = new Reservation(
                idSequence.incrementAndGet(),
                request.name(),
                request.date(),
                request.time()
        );
        reservations.add(reservation);
        return reservation;
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservations;
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservations.removeIf(r -> r.id().equals(id));
    }
}
