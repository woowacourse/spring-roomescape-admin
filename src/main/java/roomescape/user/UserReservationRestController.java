package roomescape.user;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.user.domain.Reservation;

@RestController
public class UserReservationRestController {

    private final List<Reservation> reservations = new ArrayList<>(List.of(
            new Reservation(1L, "John Doe", LocalDate.of(2023, 1, 2), LocalTime.of(10, 0)),
            new Reservation(2L, "John Doe", LocalDate.of(2023, 1, 2), LocalTime.of(10, 0)),
            new Reservation(3L, "John Doe", LocalDate.of(2023, 1, 2), LocalTime.of(10, 0))
    ));

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> retrieveReservations() {
        return ResponseEntity.ok(reservations);
    }
}
