package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.entity.ReservationTime;
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> readAll() {
        return ResponseEntity.ok(reservationTimeService.findAll());
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(
            @RequestBody ReservationTimeRequestDto reservationTimeRequestDto
    ) {
        ReservationTime reservationTime = new ReservationTime(reservationTimeRequestDto.startAt());
        return ResponseEntity.ok(reservationTimeService.save(reservationTime));
    }

    @DeleteMapping("times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
