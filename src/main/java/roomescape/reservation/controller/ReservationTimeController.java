package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.ReservationTimeRequestDto;
import roomescape.reservation.dto.ReservationTimeResponseDto;
import roomescape.reservation.entity.ReservationTime;
import roomescape.reservation.repository.ReservationTimeInMemoryRepository;

import java.util.List;

@RestController
public class ReservationTimeController {

    private final ReservationTimeInMemoryRepository repository;

    public ReservationTimeController(ReservationTimeInMemoryRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponseDto> add(@RequestBody ReservationTimeRequestDto requestDto) {
        ReservationTime reservationTime = new ReservationTime(requestDto.startAt());
        ReservationTime savedReservationTime = repository.save(reservationTime);
        return ResponseEntity.ok(ReservationTimeResponseDto.toDto(savedReservationTime));
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponseDto>> findAll() {
        List<ReservationTime> times = repository.findAll();
        List<ReservationTimeResponseDto> allReservationTimes = times.stream()
            .map(ReservationTimeResponseDto::toDto)
            .toList();

        return ResponseEntity.ok(allReservationTimes);
    }
}
