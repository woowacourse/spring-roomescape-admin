package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeServiceImpl implements TimeService {
    private final TimeRepository timeRepository;

    public TimeServiceImpl(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @Override
    public List<ReservationTime> findAllTimes() {
        return timeRepository.findAllTimes();
    }

    @Override
    public ReservationTime add(ReservationTime time) {
        return timeRepository.add(time);
    }

    @Override
    public ReservationTime findById(Long id) {
        return timeRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        timeRepository.remove(id);
    }
}
