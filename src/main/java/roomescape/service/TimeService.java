package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeRequest;
import roomescape.repository.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<ReservationTime> allTimes() {
        return timeRepository.findAll();
    }

    public long saveTime(TimeRequest timeRequest) {
        return timeRepository.save(timeRequest);
    }

    public void removeTime(long timeId) {
        timeRepository.deleteById(timeId);
    }

    public ReservationTime findTime(long timeId) {
        return timeRepository.findById(timeId);
    }
}
