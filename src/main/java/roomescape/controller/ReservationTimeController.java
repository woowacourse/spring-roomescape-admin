package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservationtime.ReservationTime;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> read() {
        List<ReservationTime> reservationTimes = reservationTimeService.read();
        return ResponseEntity.ok().body(reservationTimes);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        ReservationTime newReservationTime = reservationTimeService.create(reservationTime);
        return ResponseEntity.ok().body(newReservationTime);
    }

    @PutMapping("/times/{id}")
    public ResponseEntity<Void> update(@RequestBody ReservationTime newReservationTime, @PathVariable Long id) {
        reservationTimeService.update(newReservationTime, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
