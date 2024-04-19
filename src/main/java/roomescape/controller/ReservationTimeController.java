package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@Controller
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createReservationTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        Long id = reservationTimeDao.insert(reservationTimeRequest);
        return ResponseEntity.ok(ReservationTime.of(id, reservationTimeRequest));
    }
}
