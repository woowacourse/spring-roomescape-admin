package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.CreateReservationTimeRequest;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> readTimes() {
        List<ReservationTime> reservationTimes = reservationTimeService.readAll();
        return ResponseEntity.ok(reservationTimes);
    }

    @PostMapping
    public ResponseEntity<ReservationTime> createTime(@RequestBody CreateReservationTimeRequest request) {
        ReservationTime reservationTime = reservationTimeService.createTime(request);
        return ResponseEntity.ok(reservationTime);
    }

    // TODO: 시간이 삭제 되면 예약도 삭제되어야 하나?
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteTime(@PathVariable int id) {
        reservationTimeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
