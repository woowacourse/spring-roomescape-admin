package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.model.Time;

@Service
public class TimeService {

    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<Time> getAllTimes() {
        return timeDao.findAll();
    }

}
