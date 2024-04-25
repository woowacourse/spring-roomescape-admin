package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public List<ReservationTime> readTimes() {
        return reservationTimeService.readReservationTimes();
    }

    @GetMapping("{id}")
    public ReservationTime readTime(@PathVariable Long id) {
        return reservationTimeService.readReservationTime(id);

    }

    @PostMapping
    public ReservationTime createTime(@RequestBody ReservationTimeCreateRequest request) {
        return reservationTimeService.createTime(request);
    }

    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable Long id) {
        reservationTimeService.deleteTime(id);
    }
}
