package roomescape.admin.reservation.time;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/times")
@RestController
public class TimeController {

    private final TimeRepository timeRepository;

    @Autowired
    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @PostMapping
    public ResponseEntity<Time> create(@RequestBody TimeRequest timeRequest) {
        Time time = new Time(null, timeRequest.startAt());
        Time saveTime = timeRepository.save(time);
        return ResponseEntity.ok(saveTime);
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> findAll() {
        List<Time> times = timeRepository.findAll();
        List<TimeResponse> timeResponses = times.stream()
                .map(TimeResponse::from)
                .toList();

        return ResponseEntity.ok(timeResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        int deleteCount = timeRepository.delete(id);
        if (deleteCount == 0) {
            return ResponseEntity.ok("삭제할 시간이 존재하지 않습니다.");
        }
        return ResponseEntity.ok().build();
    }
}
