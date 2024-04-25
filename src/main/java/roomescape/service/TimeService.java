package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationTimeAddRequest;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.ReservationTime;

@Service
public class TimeService {
    private final TimeDao timeDao;
    private final ReservationDao reservationDao;

    public TimeService(TimeDao timeDao, ReservationDao reservationDao) {
        this.timeDao = timeDao;
        this.reservationDao = reservationDao;
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

    public List<ReservationTime> findAllReservationTimes() {
        return timeDao.findAll();
    }

    public void removeReservationTime(Long id) {
        if (reservationDao.isExistByTimeId(id)) {
            throw new IllegalArgumentException("해당 시간에 예약이 존재합니다.");
        }
        timeDao.delete(id);
    }

    public boolean checkReservationTimeExist(Long id) {
        return timeDao.isExist(id);
    }
}
