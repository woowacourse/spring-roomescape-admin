package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.TimeRequest;
import roomescape.controller.dto.TimeResponse;
import roomescape.domain.Time;
import roomescape.repository.TimeRepository;

import java.util.List;

@RestController
public class TimeController {
    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> createTime(@RequestBody TimeRequest timeRequest) {
        Time time = new Time(null, timeRequest.startAt());
        Time savedTime = timeRepository.insert(time);

        return ResponseEntity.ok().body(new TimeResponse(savedTime.id(), savedTime.startAt()));
    }

    @GetMapping("/times")
    @ResponseStatus(HttpStatus.OK)
    public List<TimeResponse> readTime() {
        return timeRepository.list().stream()
                .map(time -> new TimeResponse(time.id(), time.startAt()))
                .toList();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeRepository.delete(id);

        return ResponseEntity.ok().build();
    }
}
