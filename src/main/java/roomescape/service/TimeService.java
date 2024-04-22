package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

import java.util.List;

@Service
public class TimeService {

    private final ReservationTimeDao reservationTimeDao;

    public TimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> readAll() {
        return reservationTimeDao.readAll();
    }

    public int createTime(ReservationTime reservationTime) {
        return reservationTimeDao.create(reservationTime);
    }

    public void deleteTime(int id) {
        reservationTimeDao.delete(id);
    }
}
