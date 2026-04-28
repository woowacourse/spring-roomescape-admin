package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateRequest;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping
    public ResponseEntity<List<Reservation>> findAll() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> create(
            @RequestBody ReservationCreateRequest createRequest
    ) {
        Reservation createdReservation = new Reservation(
                index.getAndIncrement(),
                createRequest.name(),
                createRequest.date(),
                createRequest.time()
        );
        reservations.add(createdReservation);

        return ResponseEntity.ok(createdReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable long id
    ) {
        Reservation deleteReservation = reservations.stream()
                .filter(reservation -> Objects.equals(reservation.id(), id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 예약이 존재하지 않습니다."));
        reservations.remove(deleteReservation);

        return ResponseEntity.ok().build();
    }
}
