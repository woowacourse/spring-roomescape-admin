package roomescape.reservation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {
    private final Reservations reservations;

    public ReservationApiController(Reservations reservations) {
        this.reservations = reservations;
    }

    @PostMapping
    public ResponseEntity<Reservation> add(@RequestBody ReservationDto reservationDto) {
        return ResponseEntity.ok(reservations.add(reservationDto));
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAll() {
        return ResponseEntity.ok(reservations.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservations.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
