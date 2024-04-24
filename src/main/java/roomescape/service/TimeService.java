package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.TimeDao;

@Service
public class TimeService {

    private final TimeDao timeDao;

    public TimeService(final TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public TimeResponse save(final TimeRequest timeRequest) {
        final ReservationTime created = ReservationTime.create(timeRequest.startAt());
        final ReservationTime saved = timeDao.save(created);
        return TimeResponse.from(saved);
    }

    public List<TimeResponse> findAll() {
        return timeDao.findAll()
                .stream()
                .map(TimeResponse::from)
                .toList();
    }
}
