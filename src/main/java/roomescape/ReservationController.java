package roomescape;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<FindReservationDto>> getReservation() {
        List<FindReservationDto> reservationsDtos = reservations.stream()
                .map(FindReservationDto::of)
                .toList();
        return ResponseEntity.ok(reservationsDtos);
    }
}
