package roomescape.time.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.RequestReservationTime;
import roomescape.time.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    @PostMapping
    public ReservationTime createTime(@RequestBody RequestReservationTime request) {
        return reservationTimeService.createTime(request.getStartAt());
    }
}
