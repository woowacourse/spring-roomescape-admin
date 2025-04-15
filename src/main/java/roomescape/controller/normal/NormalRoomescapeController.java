package roomescape.controller.normal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.domain.Reservation;

@Controller
public class NormalRoomescapeController {
    private final List<Reservation> reservations = new ArrayList<>();

    public NormalRoomescapeController() {
        reservations.add(new Reservation(1, "브라운", LocalDate.of(2023,1,1), LocalTime.of(10,0)));
        reservations.add(new Reservation(2, "브라운", LocalDate.of(2023,1,2), LocalTime.of(11,0)));
        reservations.add(new Reservation(3, "브라운", LocalDate.of(2023,1,3), LocalTime.of(11,0)));
    }

    @GetMapping("/")
    public String getDefaultPage() {
        return "main/index";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }
}
