package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;

@RestController
public class TimeController {

    private final TimeService timeService;

    public TimeController(final TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> readTimes() {
        return ResponseEntity.ok(timeService.findAllTime());
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> createTime(@RequestBody final TimeRequest timeRequest) {
        return ResponseEntity.ok(timeService.createTime(timeRequest));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") final Long id) {
        int count = timeService.deleteTimeById(id);
        if (count == 0) {
            return ResponseEntity.badRequest()
                    .build();
        }
        return ResponseEntity.ok()
                .build();
    }
}
