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
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationService reservationService;

    public ReservationTimeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        return ResponseEntity.ok().body(reservationService.addReservationTime(reservationTime));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> findAll() {
        return ResponseEntity.ok().body(reservationService.findReservationTimes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
