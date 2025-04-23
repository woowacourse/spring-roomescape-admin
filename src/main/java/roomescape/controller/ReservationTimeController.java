package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.CreateReservationTimeDto;
import roomescape.entity.ReservationTime;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ReservationTime createTime(@RequestBody CreateReservationTimeDto request) {
        return reservationTimeService.createReservationTime(request);
    }

    @GetMapping
    public List<ReservationTime> getTimes() {
        return reservationTimeService.getAllReservationTimes();
    }

    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable Long id) {
        reservationTimeService.deleteReservationTime(id);
    }
}
