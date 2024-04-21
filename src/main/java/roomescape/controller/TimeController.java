package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Time;
import roomescape.dto.TimeResponse;
import roomescape.dto.TimeSaveRequest;
import roomescape.mapper.TimeMapper;
import roomescape.repository.TimeDao;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {

    private final TimeMapper timeMapper = new TimeMapper();
    private final TimeDao timeDao;

    public TimeController(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @GetMapping("/time")
    public String getAdminPage() {
        return "/admin/time";
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> getReservations() {
        List<Time> times = timeDao.findAll();
        List<TimeResponse> responses = times.stream()
                .map(timeMapper::mapToResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> createReservation(@RequestBody TimeSaveRequest request) {
        Time time = timeMapper.mapToTime(request);
        long saveId = timeDao.save(time);

        TimeResponse response = timeMapper.mapToResponse(saveId, time);
        URI location = URI.create("/time/" + saveId);
        return ResponseEntity.created(location).body(response);
    }
}
