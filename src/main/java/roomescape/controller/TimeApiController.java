package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateTimeRequest;
import roomescape.dto.TimeResponse;

@RestController
@RequestMapping("/times")
public class TimeApiController {
    private final ReservationTimeDao reservationTimeDao;

    public TimeApiController(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping
    public ResponseEntity<TimeResponse> createTime(
            @RequestBody CreateTimeRequest createTimeRequest
    ) {
        ReservationTime reservationTime = reservationTimeDao.createTime(createTimeRequest.startAt());
        return ResponseEntity.ok(TimeResponse.from(reservationTime));
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> getTimes() {
        return ResponseEntity.ok(TimeResponse.from(reservationTimeDao.findAllTimes()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") Long id) {
        reservationTimeDao.deleteTimeById(id);
        return ResponseEntity.ok().build();
    }
}
