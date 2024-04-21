package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
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
        List<ReservationTime> reservationTimes = timeDao.findAll();
        List<TimeResponse> responses = reservationTimes.stream()
                .map(timeMapper::mapToResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> createReservation(@RequestBody TimeSaveRequest request) {
        ReservationTime reservationTime = timeMapper.mapToTime(request);
        long saveId = timeDao.save(reservationTime);

        TimeResponse response = timeMapper.mapToResponse(saveId, reservationTime);
        URI location = URI.create("/times/" + saveId);
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        timeDao.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
