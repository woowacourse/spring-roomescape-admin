package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations = List.of(new Reservation(0L,"투다", LocalDate.now(), LocalTime.now()));

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservations);
    }
}
