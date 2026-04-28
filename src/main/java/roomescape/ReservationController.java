package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @PostMapping("/reservations")
    public ResponseEntity<?> postReservation(@RequestBody ReservationRequest request) {
        Reservation reservation = Reservation.toEntity(index.incrementAndGet(), request);
        reservations.add(reservation);

        ReservationResponse response = ReservationResponse.from(reservation);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/reservations")
    public ResponseEntity<?> getAllReservations() {
        return ResponseEntity.ok().body(reservations);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        reservations.remove(reservation);

        return ResponseEntity.ok().build(); // 요구사항에 맞춰서 noContent대신 ok를 return한다.
    }

}
