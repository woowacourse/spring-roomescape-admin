package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.controller.dto.ReservationTimeAddRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.service.TimeService;

@Controller
@RequestMapping("/time")
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> add(@RequestBody ReservationTimeAddRequest request) {
        ReservationTime reservationTime = timeService.addReservationTime(request);
        return ResponseEntity.ok()
                .body(ReservationTimeResponse.from(reservationTime));
    }
}
