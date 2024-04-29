package roomescape.controller.time;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> getTimes() {
        List<TimeResponse> times = timeService.getTimes();
        return ResponseEntity.ok(times);
    }

    @PostMapping
    public ResponseEntity<TimeResponse> addTime(@RequestBody final TimeRequest timeRequest) {
        TimeResponse time = timeService.addTime(timeRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .location(URI.create("/times/" + time.id()))
                .body(time);
    }
}
