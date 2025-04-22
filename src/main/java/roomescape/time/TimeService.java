package roomescape.time;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        final Time savedTime = timeDao.saveTime(notSavedTime);
        return new TimeResponse(savedTime.id(), savedTime.startAt());
    }

    public List<TimeResponse> findAllTime(){
        return timeDao.findAllTime().stream()
                .map(TimeResponse::createResponse)
                .toList();
    }
}
