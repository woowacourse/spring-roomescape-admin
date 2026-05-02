package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService timeService;

    public ReservationTimeController(ReservationTimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> read() {
        List<ReservationTime> reservationTimeList = timeService.findAll();
        return ResponseEntity.ok().body(reservationTimeList);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> post(@RequestBody ReservationTimeRequest request) {
        ReservationTime time = timeService.create(request.startAt());
        return ResponseEntity.ok(ReservationTimeResponse.from(time.getId(), time.getStartAt()));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Long removeId = (long) timeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
