package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Time;
import roomescape.dao.TimeDao;
import roomescape.dto.TimeRequestDto;
import roomescape.service.TimeService;
import roomescape.service.command.CreateTimeCommand;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<Time> create(@Valid @RequestBody TimeRequestDto timeRequest) {
        CreateTimeCommand command = CreateTimeCommand.from(timeRequest);
        Time time = timeService.create(command);
        return ResponseEntity.ok(time);
    }

    @GetMapping
    public ResponseEntity<List<Time>> findAll() {
        List<Time> times = timeService.findAll();
        return ResponseEntity.ok(times);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id){
        timeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
