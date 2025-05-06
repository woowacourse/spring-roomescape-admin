package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDAO;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

@Service
public class TimeServiceImpl implements TimeService {

    private final TimeDAO timeDAO;

    public TimeServiceImpl(final TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    @Override
    public List<TimeResponse> findAllTime() {
        final List<Time> times = timeDAO.findAllTime();
        return times.stream()
                .map(TimeResponse::from)
                .toList();
    }

    @Override
    public TimeResponse createTime(final TimeRequest timeRequest) {
        final Time time = timeRequest.toEntity();
        final Long id = timeDAO.insertTime(time);
        time.setId(id);
        return TimeResponse.from(time);
    }

    @Override
    public int deleteTimeById(final Long id) {
        return timeDAO.deleteTimeById(id);
    }

    @Override
    public boolean existsById(final Long id) {
        return timeDAO.existsById(id);
    }
}
