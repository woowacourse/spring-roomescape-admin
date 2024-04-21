package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

@Service
public class TimeService {

    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public TimeResponse createTime(TimeRequest timeRequest) {
        Time time = timeRequest.toTime();
        long id = timeDao.create(time);
        return TimeResponse.toResponse(id, timeRequest);
    }

    public List<TimeResponse> getAllTimes() {
        List<Time> times = timeDao.getAll();
        return TimeResponse.toResponses(times);
    }

    public void deleteTime(long id) {
        timeDao.delete(id);
    }
}
