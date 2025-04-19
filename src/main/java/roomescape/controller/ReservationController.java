package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong();

    @GetMapping
    public ResponseEntity<List<Reservation>> searchReservations() {
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(reservation, index.incrementAndGet());
        reservations.add(newReservation);
        return ResponseEntity.ok().body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation findReservation = reservations.stream()
                .filter(reservation -> reservation.isSameId(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다"));
        reservations.remove(findReservation);
        return ResponseEntity.ok().build();
    }
}
