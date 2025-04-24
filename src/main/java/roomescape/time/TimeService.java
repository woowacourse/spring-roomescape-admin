package roomescape.time;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.time.dto.TimeRequest;
import roomescape.time.dto.TimeResponse;

@Service
public class TimeService {

    private final TimeDao timeDao;

    public TimeService(
            @Autowired TimeDao timeDao
    ) {
        this.timeDao = timeDao;
    }


    public TimeResponse createTime(final TimeRequest request) {
        final Time notSavedTime = new Time(null, request.startAt());
        final Long savedTimeId = timeDao.saveTime(notSavedTime);
        final Time savedTime = timeDao.findTimeById(savedTimeId);
        return TimeResponse.createResponse(savedTime);
    }

    public List<TimeResponse> findAllTime() {
        return timeDao.findAllTime().stream()
                .map(TimeResponse::createResponse)
                .toList();
    }

    public void deleteTimeById(final Long id) {
        timeDao.deleteTimeById(id);
    }
}
