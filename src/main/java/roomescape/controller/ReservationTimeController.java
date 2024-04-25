package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.request.ReservationTimeCreateRequest;
import roomescape.controller.dto.response.ReservationTimeResponse;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

@RestController
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> createTime(@RequestBody ReservationTimeCreateRequest timeCreateRequest) {
        ReservationTime time = timeCreateRequest.toReservationTime();
        ReservationTime savedTime = reservationTimeDao.save(time);
        ReservationTimeResponse timeResponse = ReservationTimeResponse.fromReservationTime(savedTime);
        return ResponseEntity.ok(timeResponse);
    }
}
