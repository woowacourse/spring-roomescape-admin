package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> insertTimes(ReservationTime reservationTime) {
        return ResponseEntity.ok(reservationTimeDao.insert(reservationTime));
    }

}
