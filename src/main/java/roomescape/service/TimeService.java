package roomescape.service;

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
}
