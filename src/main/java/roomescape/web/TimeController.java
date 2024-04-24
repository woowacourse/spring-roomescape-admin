package roomescape.web;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.service.TimeService;
import roomescape.web.dto.TimeRequest;
import roomescape.web.dto.TimeResponse;

@RequestMapping("/times")
@RestController
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> findAllTime() {
        List<TimeResponse> response = timeService.findAllTime();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<TimeResponse> saveTime(@RequestBody TimeRequest request) {
        TimeResponse response = timeService.saveTime(request);
        return ResponseEntity.created(URI.create("/times/" + response.id())).body(response);
    }

    @DeleteMapping("/{time_id}")
    public ResponseEntity<Void> deleteTime(@PathVariable(value = "time_id") Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
