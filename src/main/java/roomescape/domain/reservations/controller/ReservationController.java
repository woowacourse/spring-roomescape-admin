package roomescape.domain.reservations.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservations.entity.Reservation;

@RestController
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @PostMapping("/reservations")
    public Reservation addReservation(
            @RequestBody Reservation request
    ) {
        Reservation reservation = new Reservation(
                index.getAndIncrement(),
                request.getName(),
                request.getDate().toString(),
                request.getTime().toString()
        );
        reservations.add(reservation);
        return reservation;
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservations;
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(
            @PathVariable Long id
    ) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
