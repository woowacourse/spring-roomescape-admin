package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

@Controller
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @ResponseBody
    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        return ResponseEntity.ok(reservationTimeService.create(reservationTime));
    }

    @ResponseBody
    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> readAll() {
        return ResponseEntity.ok(reservationTimeService.findAll());
    }

    @ResponseBody
    @GetMapping("/times/{id}")
    public ResponseEntity<ReservationTime> read(@PathVariable Long id) {
        return ResponseEntity.ok(reservationTimeService.findById(id));
    }

    @ResponseBody
    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
