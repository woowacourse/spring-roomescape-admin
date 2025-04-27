package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.ReservationTimeReqDTO;
import roomescape.reservation.model.ReservationTime;
import roomescape.reservation.service.ReservationTimeService;

import java.util.List;

@RequestMapping("/times")
@RestController
public class ReservationTimeController {

    private final ReservationTimeService timeService;
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService timeService, ReservationTimeService reservationTimeService) {
        this.timeService = timeService;
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTimeReqDTO timeDto) {
        return ResponseEntity.ok(reservationTimeService.create(timeDto));

    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> getAll() {
        return ResponseEntity.ok(reservationTimeService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBy(@PathVariable Long id) {
        timeService.deleteBy(id);
        return ResponseEntity.ok().build();
    }
}
