package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }


    public List<ReservationTime> findAllTimes() {
        return timeRepository.findAllTimes();
    }


    public ReservationTime add(ReservationTime time) {
        return timeRepository.add(time);
    }


    public ReservationTime findById(Long id) {
        return timeRepository.findById(id);
    }


    public void remove(Long id) {
        timeRepository.remove(id);
    }
}
