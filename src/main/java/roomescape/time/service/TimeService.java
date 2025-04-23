package roomescape.time.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import roomescape.time.dao.TimeDao;
import roomescape.time.domain.Time;
import roomescape.time.dto.TimeRequest;
import roomescape.time.dto.TimeResponse;
import roomescape.time.utils.TimeMapper;

@Service
public class TimeService {

    private final TimeDao timeDao;
    private final TimeMapper timeMapper;

    @Autowired
    public TimeService(TimeDao timeDao, TimeMapper timeMapper) {
        this.timeDao = timeDao;
        this.timeMapper = timeMapper;
    }

    public TimeResponse addTime(TimeRequest timeRequest) {
        Time time = timeDao.insert(timeMapper.toTime(timeRequest));
        return timeMapper.toTimeResponse(time);
    }

    public List<TimeResponse> findAllTimes() {
        return timeDao.findAll()
                .stream()
                .map(timeMapper::toTimeResponse)
                .toList();
    }

    public void deleteTimeById(long id) {
        timeDao.delete(id);
    }
}
