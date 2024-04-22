package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.time.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.repository.TimeRepository;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping
    public ResponseEntity<List<roomescape.dto.ReservationTimeDto>> findTimes() {
        List<roomescape.dto.ReservationTimeDto> times = timeRepository.findAll().stream()
                .map(roomescape.dto.ReservationTimeDto::from)
                .toList();
        return ResponseEntity.ok(times);
    }

    @PostMapping
    public ResponseEntity<roomescape.dto.ReservationTimeDto> createTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime newReservationTime = timeRepository.save(reservationTimeRequest.toTime());
        return ResponseEntity.created(URI.create("/times/" + newReservationTime.getId()))
                .body(roomescape.dto.ReservationTimeDto.from(newReservationTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable(value = "id") Long id) {
        try {
            timeRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
