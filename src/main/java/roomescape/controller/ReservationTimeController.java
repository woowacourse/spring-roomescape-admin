package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.AddTimeDto;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Controller
public class ReservationTimeController {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping("admin/time")
    public String adminTime() {
        return "admin/time";
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> addTime(@RequestBody AddTimeDto addTimeDto) {
        return ResponseEntity.ok(reservationTimeRepository.addTime(addTimeDto));
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> addTime() {
        return ResponseEntity.ok(reservationTimeRepository.getAllTime());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Integer> deleteTime(@PathVariable Long id) {
        return ResponseEntity.ok(reservationTimeRepository.deleteTime(id));
    }

}
