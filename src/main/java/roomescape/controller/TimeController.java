package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.TimeSlot;
import roomescape.domain.TimeSlotDto;
import roomescape.service.TimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping()
    public ResponseEntity<List<TimeSlot>> findAll() {
        List<TimeSlot> reservations = timeService.findAll();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping()
    public ResponseEntity<TimeSlot> create(@RequestBody TimeSlotDto timeSlotDto) {
        TimeSlot newTimeSlot = timeService.create(timeSlotDto);
        return ResponseEntity.ok(newTimeSlot);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
