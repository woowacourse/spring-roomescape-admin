package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dto.ReservationFindAllResponse;
import roomescape.dto.ReservationFindResponse;
import roomescape.dto.ReservationSaveRequest;

@RequestMapping("/reservations")
@Controller
public class ReservationController {

    private final AtomicLong counter = new AtomicLong();
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<ReservationFindAllResponse>> findAllReservation() {
        List<ReservationFindAllResponse> response = reservations.stream()
                .map(ReservationFindAllResponse::from)
                .toList();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ReservationFindResponse> saveReservation(@RequestBody ReservationSaveRequest request) {
        Reservation reservation = request.toEntity(counter.incrementAndGet());
        reservations.add(reservation);
        ReservationFindResponse response = ReservationFindResponse.from(reservation);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reservation_id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable(value = "reservation_id") Long id) {
        Reservation reservation = findReservationById(id);
        reservations.remove(reservation);
        return ResponseEntity.ok().build();
    }

    private Reservation findReservationById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }
}
