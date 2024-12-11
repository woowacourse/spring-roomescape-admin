package roomescape.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.service.TimeService;
import roomescape.service.dto.TimeRequest;
import roomescape.service.dto.TimeResponse;

@RequiredArgsConstructor
@RequestMapping("/times")
@RestController
public class TimeController {

    private final TimeService timeService;

    @PostMapping
    public TimeResponse createTime(@RequestBody TimeRequest timeRequest) {
        return timeService.create(timeRequest);
    }

    @GetMapping
    public List<TimeResponse> findAllTimes() {
        return timeService.findAll();
    }

    @DeleteMapping("/{id}")
    public void cancelTime(@PathVariable Long id) {
        timeService.remove(id);
    }
}
