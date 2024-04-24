package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.service.TimeService;

@Controller
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(final TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> saveTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = timeService.saveTime(reservationTimeRequest);
        return ResponseEntity.created(URI.create("/times/" + reservationTime.id()))
                .body(reservationTime);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> getTimes() {
        List<ReservationTime> reservationTimes = timeService.getTimes();
        return ResponseEntity.ok(reservationTimes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
