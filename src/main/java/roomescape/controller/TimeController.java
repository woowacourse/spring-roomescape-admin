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
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;
import roomescape.util.TimeMapper;

@RestController
@RequestMapping("times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> times() {
        return ResponseEntity.ok(convertToTimeResponses(timeService.allTimes()));
    }

    @PostMapping
    public ResponseEntity<TimeResponse> createTime(@RequestBody TimeRequest timeRequest) {
        long timeId = timeService.saveTime(timeRequest);
        TimeResponse timeResponse = TimeMapper.toResponse(timeService.findTime(timeId));
        return ResponseEntity.ok(timeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTime(@PathVariable long id) {
        timeService.removeTime(id);
        return ResponseEntity.ok().build();
    }

    private List<TimeResponse> convertToTimeResponses(List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(TimeMapper::toResponse)
                .toList();
    }
}
