package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "./admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> searchReservations() {
        reservations.add(new Reservation(1L,"브라운", LocalDate.of(2023,1,1), LocalTime.of(10,0)));
        reservations.add(new Reservation(2L,"하루", LocalDate.of(2023,2,1), LocalTime.of(11,0)));
        reservations.add(new Reservation(3L,"히포", LocalDate.of(2023,3,3), LocalTime.of(13,0)));
        return ResponseEntity.ok().body(reservations);
    }
}
