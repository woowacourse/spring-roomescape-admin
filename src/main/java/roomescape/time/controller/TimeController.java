package roomescape.time.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.domain.Time;
import roomescape.time.dto.TimeRequest;
import roomescape.time.repository.TimeRepository;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeRepository timeRepository;

    @Autowired
    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Time>> getTimes() {
        List<Time> times = timeRepository.findAll();
        return ResponseEntity.ok(times);
    }

    @PostMapping
    public ResponseEntity<Time> createTime(@RequestBody @Valid TimeRequest request) {
        Time time = request.toTime();
        Time saved = timeRepository.save(time);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
