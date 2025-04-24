package roomescape.business.service;

import org.springframework.stereotype.Service;
import roomescape.business.domain.Time;
import roomescape.presentation.dto.TimeRequest;
import roomescape.presentation.dto.TimeResponse;
import roomescape.data.dao.TimeDao;

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
