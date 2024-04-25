package roomescape.time.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.time.domain.Time;
import roomescape.time.dto.RequestTime;
import roomescape.time.dto.ResponseTime;
import roomescape.time.repository.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public Long save(RequestTime requestTime) {
        Time time = new Time(null, requestTime.startAt());
        return timeRepository.save(time);
    }

    public ResponseTime findById(Long id) {
        Time time = timeRepository.findById(id);
        return new ResponseTime(time.getId(), time.getStartAt());
    }

    public List<ResponseTime> findAll() {
        return timeRepository.findAll().stream()
                .map(time -> new ResponseTime(time.getId(), time.getStartAt()))
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        timeRepository.delete(id);
    }
}
