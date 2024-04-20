package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dto.Time;
import roomescape.dto.TimeRequest;
import roomescape.repository.TimeRepository;

@Controller
@RequestMapping("/times")
public class TimeController {

    private final TimeRepository timeRepository;

    public TimeController(final TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @PostMapping
    public ResponseEntity<Time> saveTime(@RequestBody TimeRequest timeRequest) {
        Time time = timeRepository.saveTime(timeRequest);
        return ResponseEntity.ok(time);
    }

    @GetMapping
    public ResponseEntity<List<Time>> getTimes() {
        List<Time> times = timeRepository.getTimes();
        return ResponseEntity.ok(times);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") long id) {
        timeRepository.deleteTime(id);
        return ResponseEntity.ok().build();
    }
}
