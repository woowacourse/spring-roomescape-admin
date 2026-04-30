package roomescape;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReservationController {

    private final RoomescapeService roomescapeService;

    public ReservationController(RoomescapeService roomescapeService) {
        this.roomescapeService = roomescapeService;
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> read() {
        return roomescapeService.readReservation();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> add(
            @RequestBody ReservationRequestDTO reservationRequestDTO) {
        ReservationTime reservationTime = roomescapeService.getReservationTime(
                reservationRequestDTO.timeId());
        Reservation newReservation = new Reservation(reservationRequestDTO.name(),
                reservationRequestDTO.date(), reservationTime);
        Long id = roomescapeService.addReservation(newReservation);
        Reservation finalReservation = new Reservation(id, reservationRequestDTO.name(),
                reservationRequestDTO.date(), reservationTime);
        return ResponseEntity.ok(finalReservation);
    }


    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomescapeService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<ReservationTime> add(@RequestBody Map<String, String> params) {
        ReservationTime reservationTime = new ReservationTime(null,
                LocalTime.parse(params.get("startAt")));
        ReservationTime saved = roomescapeService.addReservationTime(reservationTime);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/times")
    @ResponseBody
    public List<ReservationTime> readReservationTime() {
        return roomescapeService.findAllReservationTime();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        roomescapeService.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
