package roomescape.time.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.RequestReservationTime;
import roomescape.time.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public List<ReservationTime> getTimes() {
        return reservationTimeService.getTimes();
    }

    @PostMapping
    public ReservationTime createTime(@RequestBody RequestReservationTime request) {
        return reservationTimeService.createTime(request.startAt());
    }

    @DeleteMapping("/{id}")
    public void removeTime(@PathVariable Long id) {
        reservationTimeService.removeTime(id);
    }
}
