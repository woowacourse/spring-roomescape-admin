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
import roomescape.controller.dto.TimeSlotCreationRequest;
import roomescape.controller.dto.TimeSlotCreationResponse;
import roomescape.service.TimeSlotService;

@RestController
@RequestMapping("/times")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @GetMapping
    public ResponseEntity<List<TimeSlotCreationResponse>> getAll() {
        List<TimeSlotCreationResponse> responses = timeSlotService.getAllTimes();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<TimeSlotCreationResponse> create(@RequestBody TimeSlotCreationRequest request) {
        TimeSlotCreationResponse response = timeSlotService.addTime(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeSlotService.removeTime(id);
        return ResponseEntity.noContent().build();
    }
}
