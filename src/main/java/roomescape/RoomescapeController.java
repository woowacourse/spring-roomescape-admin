package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RoomescapeController {
    private final Reservations reservations;

    public RoomescapeController() {
        this.reservations = new Reservations();
    }

    @GetMapping("/admin")
    public String showAdminWelcome() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String showReservations() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
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
        reservations.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
