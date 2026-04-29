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
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class TimeApiController {

    private final ReservationTimeRepository reservationTimeRepository;

    public TimeApiController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> register(@RequestBody ReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(request.startAt());
        ReservationTime saved = reservationTimeRepository.save(reservationTime);
        return ResponseEntity.ok(ReservationTimeResponse.from(saved));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getAllTimes() {
        List<ReservationTimeResponse> response = reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        reservationTimeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
