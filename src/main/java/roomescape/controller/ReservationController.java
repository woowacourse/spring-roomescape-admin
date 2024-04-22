package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.Reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("reservations")
public class ReservationController {

    private final Map<Long, Reservation> reservations = new HashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping
    public List<Reservation> findAll() {
        return reservations.values().stream().toList();
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody Reservation request) {
        long id = index.getAndIncrement();
        if (reservations.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        Reservation reservation = Reservation.toEntity(id, request);
        reservations.put(id, reservation);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservations.remove(id);
        return ResponseEntity.noContent().build();
    }
}
