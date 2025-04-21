package roomescape.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.request.TimeRequest;
import roomescape.dto.response.TimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.TimeRepository;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeRepository repository;

    public TimeController(TimeRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public TimeResponse addTime(@RequestBody TimeRequest request) {
        ReservationTime reservationTime = request.toDomain();
        Long id = repository.save(reservationTime);

        return TimeResponse.toDto(ReservationTime.withId(id, reservationTime));
    }
}
