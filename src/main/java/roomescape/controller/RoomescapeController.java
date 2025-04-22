package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.ReservationRepository;
import roomescape.dto.AddReservationDto;
import roomescape.model.Reservation;

@Controller
public class RoomescapeController {

    @Autowired
    ReservationRepository reservationRepository;

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String adminReservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservationRepository.getAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody AddReservationDto addReservationDto) {
        return ResponseEntity.ok(reservationRepository.addReservation(addReservationDto));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Integer> deleteReservation(@PathVariable long id) {
        return ResponseEntity.ok().body(reservationRepository.deleteReservation(id));
    }

}
