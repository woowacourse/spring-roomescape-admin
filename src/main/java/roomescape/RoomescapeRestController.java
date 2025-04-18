package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomescapeRestController {
    private final Reservations reservations;

    public RoomescapeRestController() {
        this.reservations = new Reservations();
    }

    @GetMapping("/reservations")
    public List<ReservationDto> getAllReservation() {
        return reservations.findAll()
                .stream()
                .map(entity -> new ReservationDto(entity.date(), entity.name(), entity.time()))
                .toList();
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        ReservationEntity entity = new ReservationEntity(
                reservationDto.name(), reservationDto.date(), reservationDto.time()
        );
        reservations.save(entity);
        return ResponseEntity.ok().body(reservationDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            reservations.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
