package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping
    public List<TimeResponse> getAllTimes() {
        List<ReservationTime> reservationTimes = repository.findAll();
        return TimeResponse.toDtos(reservationTimes);
    }

    @PostMapping
    public TimeResponse addTime(@RequestBody TimeRequest request) {
        ReservationTime reservationTime = request.toDomain();
        Long id = repository.save(reservationTime);

        return TimeResponse.toDto(ReservationTime.withId(id, reservationTime));
    }

    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable(name = "id") Long id) {
        repository.deleteById(id);
    }
}
