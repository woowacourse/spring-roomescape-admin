package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeDto;
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationTimeController {
    @Autowired
    private ReservationTimeService reservationTimeService;

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> reservationTimes() {
        return ResponseEntity.ok(reservationTimeService.findAll());
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createReservationTime(@RequestBody ReservationTimeDto reservationTimeDto) {
        return ResponseEntity.ok(reservationTimeService.save(reservationTimeDto));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
