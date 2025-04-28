package roomescape.time.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.dao.ReservationTimeDao;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.TimeRequest;
import roomescape.time.dto.TimeResponse;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final ReservationTimeDao reservationTimeDao;

    public TimeController(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public List<TimeResponse> getTime() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAllTimes();

        return reservationTimes.stream()
                .map(reservationTime -> new TimeResponse(reservationTime.getId(), reservationTime.getStartAt()))
                .toList();
    }

    @PostMapping
    public TimeResponse createTime(@RequestBody TimeRequest timeRequest) {
        ReservationTime reservationTime = reservationTimeDao.insertTime(timeRequest);
        return new TimeResponse(
                reservationTime.getId(),
                reservationTime.getStartAt()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable("id") long id) {
        reservationTimeDao.deleteTime(id);
    }
}
