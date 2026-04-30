package roomescape.time.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.RequestReservationTime;
import roomescape.time.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    @PostMapping
    public ReservationTime createTime(@RequestBody RequestReservationTime request) {
        return reservationTimeService.createTime(request.getStartAt());
    }

    @GetMapping
    public List<ReservationTime> getTimes() {
        return reservationTimeService.getTimes();
    }

    @DeleteMapping("/{id}")
    public void removeTime(@PathVariable Long id) {
        reservationTimeService.removeTime(id);
    }
}
