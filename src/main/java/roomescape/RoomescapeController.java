package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.RequestReservation;

import java.util.List;

@Controller
public class RoomescapeController {

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/admin")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> reservations() {
        return reservationRepository.findAll();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> addReservationInfo(@RequestBody RequestReservation requestReservation) {
        Long id = reservationRepository.add(requestReservation);
        Reservation newReservation = requestReservation.toEntity(id);
        return ResponseEntity.ok(newReservation);
    }
//
//    @DeleteMapping("/reservations/{id}")
//    public ResponseEntity<Void> deleteReservationInfo(@PathVariable Long id) {
//        reservationInfos.removeIf(reservationInfo -> reservationInfo.getId().equals(id));
//        return ResponseEntity.ok().build();
//    }
}
