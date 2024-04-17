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
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateDto;

@RestController
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readReservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public Reservation createReservation(@RequestBody ReservationCreateDto dto) {
        Reservation reservation = new Reservation(index.getAndIncrement(), dto.getName(), dto.getDate(), dto.getTime());
        reservations.add(reservation);
        return reservation;
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        Reservation findReservation = reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 id(%d)의 예약이 존재하지 않습니다.".formatted(id)));

        reservations.remove(findReservation);
        return ResponseEntity.ok().build();
    }
}
