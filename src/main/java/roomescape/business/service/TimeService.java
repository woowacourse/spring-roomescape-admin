package roomescape.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.business.domain.Time;
import roomescape.data.dao.TimeDao;
import roomescape.exception.TimeNotFoundException;
import roomescape.presentation.dto.TimeRequest;
import roomescape.presentation.dto.TimeResponse;

@Service
public class TimeService {

    private final TimeDao timeDao;

    public TimeService(final TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public Time find(final Long id) {
        return timeDao.find(id)
                .orElseThrow(() -> new TimeNotFoundException(id));
    }

    public TimeResponse create(final TimeRequest timeRequest) {
        final Time time = timeRequest.toDomain();
        final Long id = timeDao.save(time);

        return TimeResponse.withId(id, time);
    }

    public List<TimeResponse> findAll() {
        return timeDao.findAll().stream()
                .map(TimeResponse::from)
                .toList();
    }

    public void remove(final Long id) {
        if(!timeDao.remove(id)) {
            throw new TimeNotFoundException(id);
        }
    }
}
