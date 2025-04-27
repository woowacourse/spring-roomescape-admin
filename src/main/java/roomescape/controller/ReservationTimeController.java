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
import roomescape.dto.ReservationTimeRequest;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> addTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeService.save(reservationTimeRequest);
        return ResponseEntity.ok(reservationTime);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> searchTime() {
        List<ReservationTime> times = reservationTimeService.findAll();
        return ResponseEntity.ok(times);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
