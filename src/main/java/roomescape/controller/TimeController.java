package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dto.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.repository.TimeRepository;

@Controller
@RequestMapping("/times")
public class TimeController {

    private final TimeRepository timeRepository;

    public TimeController(final TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> saveTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = timeRepository.saveTime(reservationTimeRequest);
        return ResponseEntity.ok(reservationTime);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> getTimes() {
        List<ReservationTime> reservationTimes = timeRepository.getTimes();
        return ResponseEntity.ok(reservationTimes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") long id) {
        timeRepository.deleteTime(id);
        return ResponseEntity.ok().build();
    }
}
