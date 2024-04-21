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
        Time time = new Time(timeRequest);
        long id = timeDao.create(time);
        return new TimeResponse(id, timeRequest);
    }

    public List<TimeResponse> getAllTimes() {
        List<Time> times = timeDao.getAll();
        return times.stream()
                .map(TimeResponse::new)
                .toList();
    }
}
