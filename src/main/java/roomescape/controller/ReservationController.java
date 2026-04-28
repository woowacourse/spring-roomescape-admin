package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> readAllReservation() {
        return ResponseEntity.ok(ReservationResponse.from(List.copyOf(reservations)));
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationCreateRequest request) {
        Reservation newReservation = Reservation.from(index.getAndIncrement(), request);
        reservations.add(newReservation);
        return ResponseEntity.ok(ReservationResponse.from(newReservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.id(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(reservation);
        return ResponseEntity.ok()
                .build();
    }
}
