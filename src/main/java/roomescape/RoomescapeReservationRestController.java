package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationDto;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class RoomescapeReservationRestController {
    private final Reservations reservations;

    public RoomescapeReservationRestController() {
        this.reservations = new Reservations();
    }

    @GetMapping
    public List<Reservation> getAllReservation() {
        return reservations.findAll();
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDto reservationDto) {
        Reservation entity = reservationDto.toEntity();
        try {
            reservations.save(entity);
            return ResponseEntity.ok().body(entity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        try {
            reservations.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
