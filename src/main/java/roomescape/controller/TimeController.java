package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public TimeResponse createTime(@RequestBody TimeRequest timeRequest) {
        return timeService.createTime(timeRequest);
    }

    @GetMapping
    public List<TimeResponse> getAllTimes() {
        return timeService.getAllTimes();
    }

    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable long id) {
        timeService.deleteTime(id);
    }
}
