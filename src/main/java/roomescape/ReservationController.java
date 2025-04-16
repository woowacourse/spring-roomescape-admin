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

@Controller
public class ReservationController {
    private final AtomicLong index = new AtomicLong(1);
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> reservationResponses = reservations.stream()
            .map(ReservationResponse::from)
            .toList();
        return ResponseEntity.ok().body(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(
        @RequestBody ReservationRequest request) {
        Reservation reservation = new Reservation(index.getAndIncrement(), request.name(),
            request.date(), request.time());
        reservations.add(reservation);
        return ResponseEntity.ok().body(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable(name = "id") Long id) {
        boolean isRemoved = reservations.removeIf(reservation -> reservation.getId() == id);
        if (isRemoved) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
