package roomescape.controller.normal;

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
import roomescape.controller.normal.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;

@Controller
public class NormalRoomescapeController {

    private final AtomicLong atomicLong = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/")
    public String getDefaultPage() {
        return "main/index";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationCreateRequest request) {
        Reservation reservation = new Reservation(
            atomicLong.getAndIncrement(),
            request.getName(),
            request.getDate(),
            request.getTime()
        );

        reservations.add(reservation);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        Reservation reservationToDelete = reservations.stream()
            .filter(reservation -> reservation.getId() == id)
            .findFirst()
            .orElseThrow(RuntimeException::new);

        reservations.remove(reservationToDelete);
        return ResponseEntity.ok().build();
    }
}
