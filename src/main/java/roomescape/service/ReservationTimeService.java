package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> readAll() {
        return reservationTimeDao.selectAll();
    }

    public ReservationTime register(LocalTime startAt) {
        return reservationTimeDao.insert(new ReservationTime(startAt));
    }

    public void deregister(Long id) {
        reservationTimeDao.delete(id);
    }

}
