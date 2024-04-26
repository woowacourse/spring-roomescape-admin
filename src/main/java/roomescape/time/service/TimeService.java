package roomescape.time.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.time.domain.ReservationTime;
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
        ReservationTime reservationTime = new ReservationTime(null, requestTime.startAt());
        return timeRepository.save(reservationTime);
    }

    public ResponseTime findById(Long id) {
        ReservationTime reservationTime = timeRepository.findById(id);
        return new ResponseTime(reservationTime.getId(), reservationTime.getStartAt());
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
