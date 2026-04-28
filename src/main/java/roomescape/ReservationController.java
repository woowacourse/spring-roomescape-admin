package roomescape;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final Reservations reservations;

    @Autowired
    public ReservationController(Reservations reservations) {
        this.reservations = reservations;
    }

    @GetMapping
    public ResponseEntity<List<ReservationInfo>> getReservations() {
        List<ReservationInfo> reservationsInfo = reservations.getReservationsInfo();
        return ResponseEntity.ok(reservationsInfo);
    }
}
