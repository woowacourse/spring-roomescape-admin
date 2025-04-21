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
import roomescape.CreateTimeSlotRequest;
import roomescape.ReservationTimeSlot;
import roomescape.repository.ReservationTimeSlotRepository;

@Controller
@RequestMapping("/times")
public class ReservationTimeSlotController {

    private final ReservationTimeSlotRepository repository;

    @Autowired
    public ReservationTimeSlotController(ReservationTimeSlotRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeSlot>> getAll() {
        return ResponseEntity.ok(repository.getTimeSlots());
    }

    @PostMapping
    public ResponseEntity<ReservationTimeSlot> create(@RequestBody CreateTimeSlotRequest request) {
        repository.save(request);
        return ResponseEntity.ok().build();
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
