package roomescape.time;

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
}
