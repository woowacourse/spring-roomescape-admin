package roomescape.controller.time;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.ReservationTimeMapper;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final ReservationTimeRepository timeRepository;

    public TimeController(ReservationTimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> getTimes() {
        List<TimeResponse> responses = timeRepository.findAll().stream()
                .map(ReservationTimeMapper::map)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<TimeResponse> addTime(@RequestBody TimeRequest timeRequest) {
        ReservationTime time = ReservationTimeMapper.map(timeRequest);
        ReservationTime savedTime = timeRepository.save(time);
        TimeResponse response = ReservationTimeMapper.map(savedTime);

        return ResponseEntity.ok(response);
    }
}
