package roomescape.controller;

import jakarta.validation.Valid;
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
import roomescape.dto.request.ReservationCreateRequest;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping()
    public ResponseEntity<List<Reservation>> findAll() {
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping()
    public ResponseEntity<Reservation> create(@Valid @RequestBody ReservationCreateRequest reservationCreateRequest) {

        Reservation reservation = new Reservation(index.getAndIncrement(),
                reservationCreateRequest.name(),
                reservationCreateRequest.date(),
                reservationCreateRequest.time()
        );
        reservations.add(reservation);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }
}
