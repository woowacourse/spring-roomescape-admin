package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.ReservationService;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.model.ReservationTime;

@Controller
public class ReservationTimeController {

    ReservationService reservationService;

    public ReservationTimeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("admin/time")
    public String adminTime() {
        return "admin/time";
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> addTime(@RequestBody ReservationTimeRequestDto reservationTimeRequestDto) {
        return ResponseEntity.ok(reservationService.addTime(reservationTimeRequestDto.startAt()));
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> addTime() {
        return ResponseEntity.ok(reservationService.getAllTime());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Integer> deleteTime(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.deleteTime(id));
    }

}
