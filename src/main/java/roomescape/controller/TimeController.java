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
import roomescape.domain.time.Time;
import roomescape.domain.time.repository.TimeRepository;
import roomescape.dto.time.TimeRequest;
import roomescape.dto.time.TimeResponse;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> readTimes() {
        List<TimeResponse> timeResponses = timeRepository.findAllTimes()
                .stream()
                .map(time -> TimeResponse.from(time))
                .toList();

        return ResponseEntity.ok(timeResponses);
    }

    @PostMapping
    public ResponseEntity<TimeResponse> createTime(@RequestBody TimeRequest timeRequest) {
        Time requestTime = timeRequest.toTime();
        Time responseTime = timeRepository.createTime(requestTime);

        return ResponseEntity.ok(TimeResponse.from(responseTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeRepository.deleteTime(id);

        return ResponseEntity.ok().build();
    }
}
