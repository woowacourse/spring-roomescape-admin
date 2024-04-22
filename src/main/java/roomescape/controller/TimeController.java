package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.TimeCreateRequest;
import roomescape.domain.ReservationTime;
import roomescape.service.TimeService;

import java.util.List;

@Controller
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> readTimes() {
        List<ReservationTime> data = timeService.readReservationTimes();
        return ResponseEntity.ok(data);
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationTime> readTime(@PathVariable Long id) {
        ReservationTime data = timeService.readReservationTime(id);
        return ResponseEntity.ok(data);
    }

    @PostMapping
    public ResponseEntity<ReservationTime> createTime(@RequestBody TimeCreateRequest request) {
        ReservationTime data = timeService.createTime(request);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.ok().build();
    }
}
