package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeDto;
import roomescape.entity.Time;
import roomescape.service.TimeService;

import java.util.List;

@RestController
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping("/times")
    public ResponseEntity<Time> createTime(@RequestBody TimeDto timeDto) {
        long id = timeService.addTime(timeDto);
        Time time = makeTime(timeDto, id);

        return ResponseEntity.ok(time);
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getAllTimes() {
        return ResponseEntity.ok(timeService.getAllTimes());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTimeWIthId(@PathVariable("id") long id) {
        timeService.deleteTimeWithId(id);
        return ResponseEntity.ok().build();
    }

    private Time makeTime(TimeDto timeDto, long id) {
        String startAt = timeDto.startAt();
        return new Time(id, startAt);
    }
}
