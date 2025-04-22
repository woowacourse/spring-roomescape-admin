package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.dto.ReservationTimeRequestDto;
import roomescape.reservation.dto.ReservationTimeResponseDto;
import roomescape.reservation.entity.ReservationTime;
import roomescape.reservation.repository.ReservationTimeInMemoryRepository;

import java.util.List;

@Controller
public class ReservationTimeController {

    private final ReservationTimeInMemoryRepository repository;

    public ReservationTimeController(ReservationTimeInMemoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/admin/time")
    public String adminReservationTimeDashboard() {
        return "admin/time";
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

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        repository.delete(id);
        return ResponseEntity.ok().build();
    }
}
