package roomescape.controller;

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
import roomescape.domain.time.Time;
import roomescape.dto.TimeDto;
import roomescape.dto.TimeRequest;
import roomescape.repository.TimeRepository;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping
    public ResponseEntity<List<TimeDto>> findTimes() {
        List<TimeDto> times = timeRepository.findAll().stream()
                .map(TimeDto::from)
                .toList();
        return ResponseEntity.ok(times);
    }

    @PostMapping
    public ResponseEntity<TimeDto> createTime(@RequestBody TimeRequest timeRequest) {
        Time newTime = timeRepository.save(timeRequest.toTime());
        return ResponseEntity.created(URI.create("/times/" + newTime.getId()))
                .body(TimeDto.from(newTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable(value = "id") Long id) {
        try {
            timeRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
