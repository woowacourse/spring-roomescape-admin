package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.reservation.ReservationTime;
import roomescape.controller.request.ReservationTimeRequest;
import roomescape.controller.response.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }
    // TODO 바로 정적 팩터리 메서드로 Response화
    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> addTime(@RequestBody ReservationTimeRequest timeRequestDto) {
        ReservationTime time = reservationTimeService.addTime(timeRequestDto.toEntity());
        return ResponseEntity.ok(new ReservationTimeResponse(time));
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> times() {
        List<ReservationTimeResponse> reservationTimes = ReservationTimeResponse.listOf(reservationTimeService.allReservationTimes());
        return ResponseEntity.ok(reservationTimes);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
