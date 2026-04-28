package roomescape;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class RoomescapeController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        return ResponseEntity.ok(ReservationResponse.from(List.copyOf(reservations)));
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @RequestBody ReservationCreateRequest request
    ) {
        final Reservation newReservation = new Reservation(index.incrementAndGet(), request.name(), request.date(), request.time());
        reservations.add(newReservation);

        return ResponseEntity.ok(ReservationResponse.from(newReservation));
    }

    @DeleteMapping("/{reservation-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("reservation-id") Long reservationId
    ) {
        reservations.removeIf(reservation -> Objects.equals(reservation.id(), reservationId));
        return ResponseEntity.ok(null);
    }
}
