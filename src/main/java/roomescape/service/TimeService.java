package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.TimeDto;
import roomescape.entity.Time;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public long addTime(TimeDto timeDto) {
        return timeRepository.insert(timeDto);
    }

    public List<Time> getAllTimes() {
        return timeRepository.readAll();
    }

    public void deleteTimeWithId(long id) {
        timeRepository.delete(id);
    }
}
