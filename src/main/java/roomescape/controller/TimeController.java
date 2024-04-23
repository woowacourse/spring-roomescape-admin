package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.service.TimeService;

import java.util.List;

@RestController
@RequestMapping("times")
public class TimeController {

    @Autowired
    TimeService timeService;

    @PostMapping()
    public ResponseEntity<Time> selectTime(@RequestBody Time request) {
        Time time = timeService.selectTime(request);
        return ResponseEntity.ok(time);
    }

    @GetMapping()
    public ResponseEntity<List<Time>> findAll() {
        return ResponseEntity.ok(timeService.findAll());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }

}
