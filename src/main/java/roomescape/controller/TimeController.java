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

import jakarta.validation.Valid;
import roomescape.model.Time;
import roomescape.repository.TimeRepository;

@RestController
@RequestMapping("times")
public class TimeController {

    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Time>> times() {
        return ResponseEntity.ok(timeRepository.getAll());
    }

    @PostMapping
    public ResponseEntity<Time> times(@RequestBody @Valid Time time) {
        return ResponseEntity.ok(timeRepository.save(time));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> times(@PathVariable Long id) {
        timeRepository.remove(id);
        return ResponseEntity.noContent().build();
    }
}
