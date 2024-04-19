package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateRequest;

@Controller
@RequestMapping("reservations")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok()
                .body(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody final ReservationCreateRequest reservationCreateRequest) {
        Reservation reservation = Reservation.toEntity(
                index.getAndIncrement(),
                reservationCreateRequest.name(),
                reservationCreateRequest.date(),
                reservationCreateRequest.time());

        reservations.add(reservation);
        return ResponseEntity.ok()
                .body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable final Long id) {
        final Reservation reservation;
        try {
            reservation = reservations.stream()
                    .filter(it -> Objects.equals(it.getId(), id))
                    .findFirst()
                    .orElseThrow(() ->
                            new RuntimeException("삭제할 예약이 존재하지 않습니다.")
                    );
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 예약이 존재하지 않습니다.");
        }
        reservations.remove(reservation);
        return ResponseEntity.ok()
                .build();
    }
}
