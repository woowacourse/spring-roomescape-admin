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

    @GetMapping("")
    public List<ReservationDto> getAllReservation() {
        return reservations.findAll()
                .stream()
                .map(ReservationDto::from)
                .toList();
    }

    @PostMapping("")
    public ResponseEntity<ReservationEntity> createReservation(@RequestBody ReservationDto reservationDto) {
        ReservationEntity entity = reservationDto.toEntity();
        reservations.save(entity);
        return ResponseEntity.ok().body(entity);
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
