package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.TimeRequest;
import roomescape.reservation.dto.TimeResponse;
import roomescape.reservation.model.Time;
import roomescape.reservation.model.TimeDetails;
import roomescape.reservation.repository.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public TimeResponse addTime(TimeRequest timeRequest) {
        TimeDetails timeDetails = new TimeDetails(timeRequest.startAt());
        Time time = timeRepository.insertTime(timeDetails);
        return TimeResponse.from(time);
    }

    public List<TimeResponse> getTimes() {
        List<Time> times = timeRepository.findAll();
        return times.stream()
                .map(TimeResponse::from)
                .toList();
    }

    public boolean deleteTimeById(long id) {
        return timeRepository.deleteTimeById(id);
    }
}
