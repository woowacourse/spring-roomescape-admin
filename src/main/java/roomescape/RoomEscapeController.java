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
public class RoomEscapeController {

    private final ReservationDAO reservationDAO;

    public RoomEscapeController(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> read() {
        return reservationDAO.findAllReservation();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> add(
            @RequestBody ReservationRequestDTO reservationRequestDTO) {
        ReservationTime reservationTime = reservationDAO.findReservationTimeById(
                reservationRequestDTO.timeId());
        Reservation newReservation = new Reservation(reservationRequestDTO.name(),
                reservationRequestDTO.date(), reservationTime);
        Long id = reservationDAO.add(newReservation);
        Reservation finalReservaion = new Reservation(id, reservationRequestDTO.name(),
                reservationRequestDTO.date(), reservationTime);
        return ResponseEntity.ok(finalReservaion);
    }


    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationDAO.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<ReservationTime> add(@RequestBody Map<String, String> params) {
        ReservationTime reservationTime = new ReservationTime(null,
                LocalTime.parse(params.get("startAt")));
        ReservationTime saved = reservationDAO.addReservationTime(reservationTime);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/times")
    @ResponseBody
    public List<ReservationTime> readReservationTime() {
        return reservationDAO.findAllReservationTime();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationDAO.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
