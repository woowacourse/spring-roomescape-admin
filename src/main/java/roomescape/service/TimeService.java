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
        return timeDao.add(request);
    }
}
