package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    public List<ReservationTime> getTimes() {
        return reservationTimeService.getTimes();
    }

    @PostMapping("/times")
    public ReservationTime createTime(@RequestBody ReservationTimeRequest request) {
        return reservationTimeService.createTime(request);
    }

    @DeleteMapping("/times/{id}")
    public void deleteTime(@PathVariable Long id) {
        reservationTimeService.deleteTime(id);
    }
}
