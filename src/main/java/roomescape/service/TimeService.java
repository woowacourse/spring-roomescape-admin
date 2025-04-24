package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.dao.TimeDao;

@Service
public class TimeService {

    private final TimeDao timeDao;

    public TimeService(final TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public TimeResponse create(final TimeRequest timeRequest) {
        final Time time = timeRequest.toDomain();
        final Long id = timeDao.save(time);

        return TimeResponse.withId(id, time);
    }
}
