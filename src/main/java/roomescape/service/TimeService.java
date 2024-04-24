package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.web.dto.TimeFindAllResponse;
import roomescape.web.dto.TimeSaveRequest;
import roomescape.web.dto.TimeSaveResponse;

@Service
public class TimeService {
    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<TimeFindAllResponse> findAllTime() {
        List<Time> times = timeDao.findAll();
        return times.stream()
                .map(TimeFindAllResponse::from)
                .toList();
    }

    public TimeSaveResponse saveTime(TimeSaveRequest request) {
        Time time = request.toTime();
        timeDao.save(time);
        return TimeSaveResponse.from(time);
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
