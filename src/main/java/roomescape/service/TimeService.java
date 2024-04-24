package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.entity.ReservationTime;
import roomescape.repository.TimeRepository;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<ReservationTime> readAll() {
        return timeRepository.readAll();
    }

    public ReservationTime save(ReservationTime reservationTime) {
        return timeRepository.save(reservationTime);
    }

    public void deleteById(long id) {
        timeRepository.deleteById(id);
    }
}
