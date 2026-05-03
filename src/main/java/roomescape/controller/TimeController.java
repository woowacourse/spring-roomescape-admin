package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;

import java.util.List;

@Controller
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/times")
    @ResponseBody
    public List<TimeResponse> findAllTimes() {
        return timeService.findAllTimes().stream()
                .map(TimeResponse::from)
                .toList();
    }

    @PostMapping("/times")
    @ResponseBody
    public TimeResponse addTime(@RequestBody TimeRequest request) {
        return TimeResponse.from(timeService.add(request.toDomain()));
    }

    @DeleteMapping("/times/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteTime(@PathVariable("id") Long id) {
        timeService.remove(id);
        return ResponseEntity.ok().build();
    }
}
