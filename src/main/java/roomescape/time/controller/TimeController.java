package roomescape.time.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.dao.TimeDAO;
import roomescape.time.domain.Time;
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
        List<Time> times = timeDAO.findAllTimes();

        return times.stream()
                .map(time -> new TimeResponse(time.getId(), time.getLocalTime()))
                .toList();
    }
}
