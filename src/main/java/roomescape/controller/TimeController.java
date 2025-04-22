package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.AddTimeDto;
import roomescape.model.Time;
import roomescape.repository.TimeRepository;

@Controller
public class TimeController {
    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping("admin/time")
    public String adminTime() {
        return "admin/time";
    }

    @PostMapping("/times")
    public ResponseEntity<Time> addTime(@RequestBody AddTimeDto addTimeDto) {
        return ResponseEntity.ok(timeRepository.addTime(addTimeDto));
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> addTime() {
        return ResponseEntity.ok(timeRepository.getAllTime());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Integer> deleteTime(@PathVariable Long id) {
        return ResponseEntity.ok(timeRepository.deleteTime(id));
    }

}
