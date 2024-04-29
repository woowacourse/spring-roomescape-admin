package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.entity.ReservationTime;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeApiController {

    private final ReservationTimeService timeService;

    public ReservationTimeApiController(ReservationTimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public List<ReservationTimeResponse> findAllReservationTimes() {
        return timeService.findAllReservationTimes().stream()
                .map(ReservationTimeResponse::new)
                .toList();
    }

    @PostMapping
    public ReservationTimeResponse addReservationTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime newReservationTime = timeService.addReservationTime(reservationTimeRequest);

        return new ReservationTimeResponse(newReservationTime);
    }

    @DeleteMapping("/{id}")
    public void deleteReservationTime(@PathVariable long id) {
        timeService.deleteReservationTimeById(id);
    }
}
