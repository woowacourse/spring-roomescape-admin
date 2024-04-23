package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationTimeResponse;
import roomescape.service.dto.SaveReservationTimeRequest;
import roomescape.service.reservationTime.ReservationTimeCreateService;

@RestController
public class ReservationTimeController {

    private final ReservationTimeCreateService reservationTimeCreateService;

    public ReservationTimeController(ReservationTimeCreateService reservationTimeCreateService) {
        this.reservationTimeCreateService = reservationTimeCreateService;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> addReservationTime(@RequestBody SaveReservationTimeRequest request) {
        ReservationTime reservationTime = reservationTimeCreateService.createReservationTime(request);
        return ResponseEntity.ok(ReservationTimeResponse.of(reservationTime));
    }
}
