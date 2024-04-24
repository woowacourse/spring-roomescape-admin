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
import roomescape.mapper.ReservationMapper;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeApiController {

    private final ReservationTimeService timeService;
    private final ReservationMapper mapper;

    public ReservationTimeApiController(ReservationTimeService timeService, ReservationMapper mapper) {
        this.timeService = timeService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ReservationTimeResponse> findAllReservationTimes() {
        return timeService.findAllReservationTimes().stream()
                .map(mapper::mapReservationTimeToResponse)
                .toList();
    }

    @PostMapping
    public ReservationTimeResponse addReservationTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime newReservationTime = timeService.addReservationTime(reservationTimeRequest);

        return mapper.mapReservationTimeToResponse(newReservationTime);
    }

    @DeleteMapping("/{id}")
    public void deleteReservationTime(@PathVariable long id) {
        timeService.deleteReservationTimeById(id);
    }
}
