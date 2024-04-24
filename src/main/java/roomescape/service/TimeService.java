package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.web.dto.TimeRequest;
import roomescape.web.dto.TimeResponse;

@Service
public class TimeService {
    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<TimeResponse> findAllTime() {
        List<Time> times = timeDao.findAll();
        return times.stream()
                .map(TimeResponse::from)
                .toList();
    }

    public TimeResponse saveTime(TimeRequest request) {
        Time time = request.toTime();
        timeDao.save(time);
        return TimeResponse.from(time);
    }

    public void deleteTime(Long id) {
        Time time = findTimeById(id);
        timeDao.delete(time);
    }

    private Time findTimeById(Long id) {
        return timeDao.findById(id)
                .orElseThrow();
    }
}
