package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationTimeDto;
import roomescape.entity.ReservationTime;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createTime(@RequestBody ReservationTimeDto reservationTimeDto) {
        long timeId = reservationTimeService.addTime(reservationTimeDto);
        ReservationTime reservationTime = makeTime(reservationTimeDto, timeId);

        return ResponseEntity.status(HttpStatus.CREATED).body(reservationTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> getAllTimes() {
        return ResponseEntity.ok(reservationTimeService.getAllTimes());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") long timeId) {
        reservationTimeService.deleteTime(timeId);
        return ResponseEntity.noContent().build();
    }

    private ReservationTime makeTime(ReservationTimeDto reservationTimeDto, long timeId) {
        String startAt = reservationTimeDto.startAt();
        return new ReservationTime(timeId, startAt);
    }
}
