package roomescape.time.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.dao.TimeDAO;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.TimeRequest;
import roomescape.time.dto.TimeResponse;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeDAO timeDAO;

    public TimeController(final TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    @GetMapping
    public List<TimeResponse> getTime() {
        List<ReservationTime> reservationTimes = timeDAO.findAllTimes();

        return reservationTimes.stream()
                .map(reservationTime -> new TimeResponse(reservationTime.getId(), reservationTime.getStartAt()))
                .toList();
    }

    @PostMapping
    public TimeResponse createTime(@RequestBody TimeRequest timeRequest) {
        ReservationTime reservationTime = timeDAO.insertTime(timeRequest);
        return new TimeResponse(
                reservationTime.getId(),
                reservationTime.getStartAt()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable("id") long id) {
        timeDAO.deleteTime(id);
    }
}
