package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationTimeAddRequest;
import roomescape.dao.TimeDao;
import roomescape.domain.ReservationTime;

@Service
public class TimeService {
    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public ReservationTime addReservationTime(ReservationTimeAddRequest request) {
        if (isDuplicateTimeRegistered(request)) {
            throw new IllegalArgumentException("이미 등록된 시간입니다.");
        }
        return timeDao.add(request);
    }

    private boolean isDuplicateTimeRegistered(ReservationTimeAddRequest time) {
        return timeDao.isExist(time.startTime());
    }
}
