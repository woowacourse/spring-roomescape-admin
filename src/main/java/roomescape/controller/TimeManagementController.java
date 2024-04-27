package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.service.TimeManagementService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeManagementController {
    private final TimeManagementService timeManagementService;

    public TimeManagementController(TimeManagementService timeManagementService) {
        this.timeManagementService = timeManagementService;
    }

    @GetMapping
    public List<Time> read() {
        return timeManagementService.read();
    }

    @PostMapping
    public Time create(@RequestBody TimeRequest timeRequest) {
        return timeManagementService.create(timeRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        timeManagementService.delete(id);
    }
}
