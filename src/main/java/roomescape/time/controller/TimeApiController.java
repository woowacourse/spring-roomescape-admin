package roomescape.time.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.domain.Time;
import roomescape.time.dto.TimeRequest;
import roomescape.time.service.TimeService;

@RestController
public class TimeApiController {
    private final TimeService timeService;

    public TimeApiController(final TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/times")
    public List<Time> findAll() {
        return timeService.findAll();
    }

    @PostMapping("/times")
    public ResponseEntity<Time> create(@RequestBody TimeRequest timeRequest) {
        Time time = timeService.save(timeRequest);
        return ResponseEntity.ok(time);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        timeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
