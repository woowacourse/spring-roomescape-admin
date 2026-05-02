package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.entity.ReservationTime;
import roomescape.domain.service.ReservationTimeService;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> postReservationTime(
            @RequestBody @Valid ReservationTimeRequestDto reservationTimeRequestDto
    ) {
        return ResponseEntity.ok(reservationTimeService.postReservationTime(reservationTimeRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> getReservationTimes() {
        return ResponseEntity.ok(reservationTimeService.getAllReservationTime());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.deleteReservationTimeBy(id);
        return ResponseEntity.ok().build();
    }
}
