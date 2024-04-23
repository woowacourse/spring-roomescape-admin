package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationTimeResponse;
import roomescape.service.dto.SaveReservationTimeRequest;
import roomescape.service.reservationTime.ReservationTimeCreateService;
import roomescape.service.reservationTime.ReservationTimeFindService;

import java.util.List;

@RestController
public class ReservationTimeController {

    private final ReservationTimeCreateService reservationTimeCreateService;
    private final ReservationTimeFindService reservationTimeFindService;

    public ReservationTimeController(ReservationTimeCreateService reservationTimeCreateService, ReservationTimeFindService reservationTimeFindService) {
        this.reservationTimeCreateService = reservationTimeCreateService;
        this.reservationTimeFindService = reservationTimeFindService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeFindService.findReservationTimes();
        return ResponseEntity.ok(ReservationTimeResponse.listOf(reservationTimes));
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> addReservationTime(@RequestBody SaveReservationTimeRequest request) {
        ReservationTime reservationTime = reservationTimeCreateService.createReservationTime(request);
        return ResponseEntity.ok(ReservationTimeResponse.of(reservationTime));
    }
}
