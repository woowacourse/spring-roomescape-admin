package roomescape.time.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.controller.request.TimeCreateRequest;
import roomescape.time.controller.response.TimeResponse;
import roomescape.time.domain.Time;

@RestController
public class TimeApiController {

    private final TimeRepository timeRepository;

    public TimeApiController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> createTime(@RequestBody TimeCreateRequest timeCreateRequest) {
        Time time = timeCreateRequest.to();

        Time saved = timeRepository.save(time);

        return ResponseEntity.ok(TimeResponse.from(saved));
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> getTimes() {
        List<Time> times = timeRepository.findAll();

        return ResponseEntity.ok(TimeResponse.from(times));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

}
