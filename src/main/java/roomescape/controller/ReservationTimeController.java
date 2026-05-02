package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeDetailDto;
import roomescape.dto.ReservationTimeSaveDto;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeDetailDto>> getReservationTimes() {
        List<ReservationTimeDetailDto> responseData = reservationTimeService.readAll().stream()
                .map(ReservationTimeDetailDto::from)
                .toList();
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeDetailDto> createReservationTime(@RequestBody ReservationTimeSaveDto dto) {
        ReservationTime savedReservationTime = reservationTimeService.register(dto.startAt());
        ReservationTimeDetailDto responseData = ReservationTimeDetailDto.from(savedReservationTime);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseData);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.deregister(id);
        return ResponseEntity.ok().build();
    }

}
