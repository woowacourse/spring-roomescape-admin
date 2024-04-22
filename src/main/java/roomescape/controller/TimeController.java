package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.TimeCreateRequest;
import roomescape.domain.ReservationTime;
import roomescape.service.TimeService;

@Controller
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
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
}
