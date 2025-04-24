package roomescape;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/times")
@RestController
public class TimeController {

    private final ReservationTimeDao reservationTimeRepository;

    public TimeController(ReservationTimeDao reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> create(@RequestBody @Valid ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toEntity();
        ReservationTime saved = reservationTimeRepository.save(reservationTime);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> getTimes() {
        List<ReservationTime> times = reservationTimeRepository.findAll();
        return ResponseEntity.ok(times);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            reservationTimeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("예약 시간을 찾을 수 없습니다."));
            reservationTimeRepository.delete(id);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
