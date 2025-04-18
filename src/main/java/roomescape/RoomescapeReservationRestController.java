package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class RoomescapeReservationRestController {
    private final Reservations reservations;

    public RoomescapeReservationRestController() {
        this.reservations = new Reservations();
    }

    @GetMapping("/")
    public List<ReservationDto> getAllReservation() {
        return reservations.findAll()
                .stream()
                .map(entity -> new ReservationDto(entity.date(), entity.name(), entity.time()))
                .toList();
    }

    @PostMapping("/")
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        reservations.save(reservationDto.toEntity());
        return ResponseEntity.ok().body(reservationDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            reservations.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
