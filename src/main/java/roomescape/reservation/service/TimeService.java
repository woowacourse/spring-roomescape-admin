package roomescape.reservation.service;


import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.controller.dto.TimeRequest;
import roomescape.reservation.controller.dto.TimeResponse;
import roomescape.reservation.domain.Time;
import roomescape.reservation.domain.repository.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public TimeResponse add(TimeRequest request) {
        Time newTime = request.toTimeWithoutId();
        Long id = timeRepository.saveAndReturnId(request.toTimeWithoutId());
        return TimeResponse.from(newTime.withId(id));
    }

    public void remove(Long id) {
        timeRepository.deleteById(id);
    }

    public List<TimeResponse> getTimes() {
        return timeRepository.findAll().stream()
                .map(TimeResponse::from)
                .toList();
    }

}
