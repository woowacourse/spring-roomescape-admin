package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entity.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private AtomicLong index = new AtomicLong(1);
    private Map<Long, Reservation> reservations = new HashMap<>();

    @GetMapping
    public List<Reservation> getReservations(
    ) {
        return reservations.values().stream().toList();
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            String name,
            LocalDate date,
            LocalTime time
    ) {
        Reservation reservation = new Reservation(index.getAndIncrement(), name, date, time);

        reservations.put(reservation.getId(), reservation);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Reservation>> deleteReservation(
            @PathVariable Long id
    ) {
        reservations.remove(id);

        return ResponseEntity.ok().build();
    }

}
