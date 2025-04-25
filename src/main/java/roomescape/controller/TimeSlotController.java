package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.controller.dto.CreateTimeSlotRequest;
import roomescape.model.TimeSlot;
import roomescape.service.TimeSlotService;

@Controller
@RequestMapping("/times")
public class TimeSlotController {

    private final TimeSlotService service;

    @Autowired
    public TimeSlotController(final TimeSlotService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TimeSlot> add(@RequestBody CreateTimeSlotRequest request) {
        TimeSlot added = service.add(request.startAt());
        return ResponseEntity.ok(added);
    }

    @GetMapping
    public ResponseEntity<List<TimeSlot>> allTimeSlots() {
        return ResponseEntity.ok(service.allTimeSlots());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        boolean isRemoved = service.removeById(id);
        if (isRemoved) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
