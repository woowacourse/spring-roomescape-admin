package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeDao timeDao;

    public ReservationTimeController(ReservationTimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> addReservationTime(@RequestBody ReservationTimeRequest request) {
        ReservationTimeResponse response = timeDao.insert(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
