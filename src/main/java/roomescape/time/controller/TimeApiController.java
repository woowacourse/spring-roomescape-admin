package roomescape.time.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.dto.RequestTime;
import roomescape.time.dto.ResponseTime;
import roomescape.time.service.TimeService;

@RestController
public class TimeApiController {

    private final TimeService timeService;

    public TimeApiController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ResponseTime>> findAll() {
        List<ResponseTime> times = timeService.findAll();

        return ResponseEntity.ok(times);
    }

    @PostMapping("/times")
    public ResponseEntity<ResponseTime> save(@RequestBody RequestTime requestTime) {
        Long saveId = timeService.save(requestTime);
        ResponseTime responseTime = timeService.findById(saveId);

        return ResponseEntity.ok(responseTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.delete(id);

        return ResponseEntity.ok().build();
    }
}
