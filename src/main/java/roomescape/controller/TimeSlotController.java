package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dto.CreateTimeSlotRequest;
import roomescape.model.TimeSlot;
import roomescape.repository.TimeSlotRepository;

@Controller
@RequestMapping("/times")
public class TimeSlotController {

    private final TimeSlotRepository repository;

    @Autowired
    public TimeSlotController(TimeSlotRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<TimeSlot>> getAllTimeSlots() {
        return ResponseEntity.ok(repository.getTimeSlots());
    }

    @PostMapping
    public ResponseEntity<TimeSlot> create(@RequestBody CreateTimeSlotRequest request) {
        final var savedId = repository.save(request);
        final var saved = repository.findById(savedId).get();
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        boolean isRemoved = repository.removeById(id);
        if (isRemoved) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
