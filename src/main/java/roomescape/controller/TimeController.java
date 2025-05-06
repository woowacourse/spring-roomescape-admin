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
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(final TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> readTimes() {
        return ResponseEntity.ok(timeService.findAllTime());
    }

    @PostMapping
    public ResponseEntity<TimeResponse> createTime(@RequestBody final TimeRequest timeRequest) {
        return ResponseEntity.ok(timeService.createTime(timeRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") final Long id) {
        if (!timeService.existsById(id)) {
            return ResponseEntity.badRequest()
                    .build();
        }
        timeService.deleteTimeById(id);
        return ResponseEntity.ok()
                .build();
    }
}
