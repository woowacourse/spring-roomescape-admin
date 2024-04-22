package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeDao;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> addReservationTime(
        @RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime time = ReservationTimeRequest.from(reservationTimeRequest);
        ReservationTime savedTime = reservationTimeDao.save(time);
        ReservationTimeResponse reservationTimeResponse = ReservationTimeResponse.from(savedTime);
        return ResponseEntity.ok()
            .body(reservationTimeResponse);
    }
}
