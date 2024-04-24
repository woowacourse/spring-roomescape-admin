package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

import java.util.List;

@Controller
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> readTimes() {
        List<ReservationTime> data = reservationTimeService.readReservationTimes();
        return ResponseEntity.ok(data);
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationTime> readTime(@PathVariable Long id) {
        ReservationTime data = reservationTimeService.readReservationTime(id);
        return ResponseEntity.ok(data);

    }

    @PostMapping
    public ResponseEntity<ReservationTime> createTime(@RequestBody ReservationTimeCreateRequest request) {
        ReservationTime data = reservationTimeService.createTime(request);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        reservationTimeService.deleteTime(id);
        return ResponseEntity.ok().build();
    }
}
