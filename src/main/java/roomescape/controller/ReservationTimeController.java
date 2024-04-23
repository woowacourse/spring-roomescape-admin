package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.reservation.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponseDto> addTime(@RequestBody ReservationTimeRequestDto timeRequestDto) {
        ReservationTime time = reservationTimeService.addTime(timeRequestDto.toEntity());
        return ResponseEntity.ok(new ReservationTimeResponseDto(time));
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponseDto>> times() {
        List<ReservationTimeResponseDto> reservationTimes = ReservationTimeResponseDto.listOf(reservationTimeService.allReservationTimes());
        return ResponseEntity.ok(reservationTimes);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
