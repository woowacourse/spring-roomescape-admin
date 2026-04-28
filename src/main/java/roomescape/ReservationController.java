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

    private final ReservationsDao reservationsDao;

    @Autowired
    public ReservationController(InMemoryReservationsDao reservationsDao) {
        this.reservationsDao = reservationsDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationInfo>> getReservationsDao() {
        List<Reservation> reservations = reservationsDao.getReservationsInfo();

        List<ReservationInfo> reservationsInfo = reservations.stream()
                .map(ReservationInfo::from)
                .toList();

        return ResponseEntity.ok(reservationsInfo);
    }
}
