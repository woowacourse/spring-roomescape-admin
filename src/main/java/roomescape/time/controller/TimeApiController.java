package roomescape.time.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.dto.TimeResponse;
import roomescape.time.dto.TimeSaveRequest;
import roomescape.time.service.TimeService;

@RestController
public class TimeApiController {
    private final TimeService timeService;

    public TimeApiController(final TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/times")
    public List<TimeResponse> findAll() {
        return timeService.findAll();
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> create(@RequestBody TimeSaveRequest timeSaveRequest) {
        TimeResponse response = timeService.save(timeSaveRequest);
        return ResponseEntity.created(URI.create("/times/" + response.getId()))
                .body(response);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        timeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
