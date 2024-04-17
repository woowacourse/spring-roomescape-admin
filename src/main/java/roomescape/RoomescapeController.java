package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class RoomescapeController {
    private List<Reservation> reservations = List.of(new Reservation((long) 1,"a", LocalDate.now(),LocalTime.now()));

    @GetMapping("admin")
    public String admin(){
        return "/admin/index";
    }

    @GetMapping("admin/reservation")
    public String reservation() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> read(){
        return ResponseEntity.ok().body(reservations);
    }

}
