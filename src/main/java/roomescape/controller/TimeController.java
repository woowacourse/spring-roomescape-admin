package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.controller.dto.TimeRequest;
import roomescape.controller.dto.TimeResponse;
import roomescape.domain.Time;
import roomescape.repository.TimeRepository;

import java.util.List;

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
    public ResponseEntity<List<TimeResponse>> readTime() {
        return ResponseEntity.ok().body(
                timeRepository.list().stream()
                        .map(time -> new TimeResponse(time.id(), time.startAt()))
                        .toList()
        );
    }

    @DeleteMapping("/times")
    public ResponseEntity<Void> deleteTime(@RequestBody Long id) {
        timeRepository.delete(id);

        return ResponseEntity.ok().build();
    }
}
