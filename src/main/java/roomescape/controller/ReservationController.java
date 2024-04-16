package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservationDatum() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservationData(@RequestBody Reservation request) {
        Reservation reservation = new Reservation(index.getAndIncrement(), request);
        reservations.add(reservation);

        return ResponseEntity.ok(reservation);
    }
}
