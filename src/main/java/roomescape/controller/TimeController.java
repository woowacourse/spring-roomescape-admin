package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.repository.TimeRepository;

import java.util.List;

@Controller
public class TimeController {
    private final TimeRepository repository;

    public TimeController(TimeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/times")
    @ResponseBody
    public List<TimeResponse> findAllTimes() {
        return repository.findAllTimes().stream()
                .map(TimeResponse::from)
                .toList();
    }

    @PostMapping("/times")
    @ResponseBody
    public TimeResponse addTime(@RequestBody TimeRequest request) {
        Time saved = repository.add(request.toDomain());
        return TimeResponse.from(saved);
    }

    @DeleteMapping("/times/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteTime(@PathVariable("id") Long id) {
        repository.remove(id);
        return ResponseEntity.ok().build();
    }
}
