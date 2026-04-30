package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public List<ReservationTime> getTimes() {
        return reservationTimeService.findAll();
    }

    @PostMapping
    public ReservationTime createTime(@RequestBody final ReservationTimeCreateRequest request) {
        return reservationTimeService.create(request.startAt());
    }

    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable final long id) {
        reservationTimeService.delete(id);
    }
}
