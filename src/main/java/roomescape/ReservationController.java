package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {

    private final Reservations reservations = new Reservations();

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readAll() {
        return ResponseEntity.ok(reservations.getAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationReqDto dto) {
        Reservation newReservation = reservations.addAndGet(dto);
        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservations.delete(id);
        return ResponseEntity.ok().build();
    }
}
