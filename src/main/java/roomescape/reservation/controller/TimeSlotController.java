package roomescape.reservation.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.TimeSlotRequest;
import roomescape.reservation.dto.TimeSlotResponse;
import roomescape.reservation.service.TimeSlotService;

@RestController
@RequestMapping("/times")
public class TimeSlotController {
    private final TimeSlotService timeSlotService;

    public TimeSlotController(final TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @PostMapping
    public ResponseEntity<TimeSlotResponse> create(@RequestBody TimeSlotRequest timeSlotRequest) {
        return ResponseEntity.ok(timeSlotService.create(timeSlotRequest));
    }

    @GetMapping
    public ResponseEntity<List<TimeSlotResponse>> findAll() {
        return ResponseEntity.ok(timeSlotService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long timeId) {
        boolean deleted = timeSlotService.delete(timeId);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
