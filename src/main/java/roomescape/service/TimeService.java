package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.web.dto.TimeFindAllResponse;

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
}
