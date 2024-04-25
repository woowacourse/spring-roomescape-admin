package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("times")
public class ReservationTimeController {

    @Autowired
    ReservationTimeService reservationTimeService;

    @PostMapping()
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTime request) {
        ReservationTime reservationTime = reservationTimeService.create(request);
        return ResponseEntity.ok(reservationTime);
    }

    @GetMapping()
    public ResponseEntity<List<ReservationTime>> findAll() {
        return ResponseEntity.ok(reservationTimeService.findAll());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
