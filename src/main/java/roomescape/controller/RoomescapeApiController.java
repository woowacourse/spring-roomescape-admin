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
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class RoomescapeApiController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(index.getAndIncrement(),
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time());
        reservations.add(reservation);
        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> response = reservations.stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .ifPresent(reservations::remove);
        return ResponseEntity.ok().build();
    }
}
