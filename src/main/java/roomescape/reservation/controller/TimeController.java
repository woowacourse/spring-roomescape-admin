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
import roomescape.reservation.controller.dto.TimeRequest;
import roomescape.reservation.controller.dto.TimeResponse;

import roomescape.reservation.service.TimeService;

@RequestMapping("/times")
@RestController
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public TimeResponse addTime(@RequestBody TimeRequest request){
        return timeService.add(request);
    }

    @GetMapping
    public List<TimeResponse> getTimes(){
        return timeService.getTimes();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") Long id){
        timeService.remove(id);
        return ResponseEntity.noContent().build();
    }

}
