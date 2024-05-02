package roomescape.time.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.dto.ReservationTimeRequest;
import roomescape.time.dto.ReservationTimeResponse;
import roomescape.time.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> reservationTimeSave(
            @RequestBody ReservationTimeRequest reservationTimeRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(timeService.addReservationTime(reservationTimeRequest));
    }

    @GetMapping
    public List<ReservationTimeResponse> reservationTimesList() {
        return timeService.findReservationTimes();
    }

    @DeleteMapping("/{reservationTimeId}")
    public ResponseEntity<Void> reservationTimeRemove(@PathVariable long reservationTimeId) {
        timeService.removeReservationTime(reservationTimeId);
        return ResponseEntity.noContent().build();
    }
}
