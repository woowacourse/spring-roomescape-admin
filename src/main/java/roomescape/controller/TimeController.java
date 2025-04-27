package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.TimeDAO;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

@RestController
public class TimeController {

    private final TimeDAO timeDAO;

    public TimeController(final TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> readTimes() {
        final List<Time> times = timeDAO.findAllTime();
        final List<TimeResponse> timeResponses = times.stream()
                .map(TimeResponse::from)
                .toList();
        return ResponseEntity.ok(timeResponses);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> createTime(@RequestBody final TimeRequest timeRequest) {
        final Time time = timeRequest.toEntity();
        final Long id = timeDAO.insertTime(time);
        if (id == -1L) {
            return ResponseEntity.badRequest()
                    .build();
        }
        time.setId(id);
        return ResponseEntity.ok(TimeResponse.from(time));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") final Long id) {
        int count = timeDAO.deleteTimeById(id);
        if (count == 0) {
            return ResponseEntity.badRequest()
                    .build();
        }
        return ResponseEntity.ok()
                .build();
    }
}
