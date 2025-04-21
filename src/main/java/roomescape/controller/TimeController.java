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
import roomescape.dto.CreateReservationTimeDto;
import roomescape.dto.ReservationTimeDto;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public TimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public List<ReservationTimeDto> getAllTime() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeDto::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ReservationTimeDto> createReservationTime(
            @RequestBody CreateReservationTimeDto createReservationTimeDto) {
        ReservationTime savedReservationTime = reservationTimeRepository.add(
                createReservationTimeDto.toReservationTime());
        return ResponseEntity.ok(ReservationTimeDto.from(savedReservationTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable("id") Long id) {
        reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다."));
        reservationTimeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
