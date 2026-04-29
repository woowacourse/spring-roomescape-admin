package roomescape;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.request.CreateReservationTimeRequest;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping()
    public ResponseEntity<ReservationTime> createReservation(@RequestBody CreateReservationTimeRequest request) {
        ReservationTime reservationTime = ReservationTime.createWithoutId(request.startAt());
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);
        return ResponseEntity.ok(savedReservationTime);
    }

    @GetMapping()
    public ResponseEntity<List<ReservationTime>> getReservationTimes() {
        List<ReservationTime> reservations = reservationTimeDao.findAllReservationTimes();
        return ResponseEntity.ok(reservations);
    }
}
