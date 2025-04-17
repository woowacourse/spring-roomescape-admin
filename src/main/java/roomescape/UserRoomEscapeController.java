package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class UserRoomEscapeController {

    private static final AtomicLong autoIncrement = new AtomicLong(0);
    private static final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> reservations() {
        List<ReservationResponse> response = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservations(@RequestBody ReservationCreateRequest request) {
        Reservation newReservation = request.toDomain(autoIncrement.incrementAndGet());

        reservations.add(newReservation);

        return ResponseEntity.ok(ReservationResponse.from(newReservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservations(@PathVariable("id") Long id) {
        Reservation target = reservations.stream()
                .filter(reservation -> Objects.equals(reservation.id(), id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        reservations.remove(target);

        return ResponseEntity.ok().build();
    }

    public static void clear() {
        reservations.clear();
        autoIncrement.set(0);
    }
}
