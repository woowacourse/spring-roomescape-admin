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
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        ReservationTime savedReservationTime = reservationTimeService.create(reservationTime);
        return ResponseEntity.ok(savedReservationTime);
    }

    @GetMapping("/times")
    public List<ReservationTime> read() {
        return reservationTimeService.readAll();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
