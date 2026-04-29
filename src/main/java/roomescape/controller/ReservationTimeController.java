package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@RestController
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    @Autowired
    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping("/times")
    public ReservationTimeResponse createReservationTime(@RequestBody ReservationTimeRequest request) {
        return reservationTimeDao.create(request);

    }
}
