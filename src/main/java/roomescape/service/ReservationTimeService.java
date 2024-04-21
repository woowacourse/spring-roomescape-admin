package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.entity.ReservationTime;
import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        return reservationTimeDao.createReservationTime(reservationTime);
    }

    public ReservationTime findReservationTime(Long id) {
        return reservationTimeDao.findReservationTime(id);
    }

    public List<ReservationTime> findReservationTimes() {
        return reservationTimeDao.findReservationTimes();
    }

    public void removeReservationTime(Long id) {
        reservationTimeDao.removeReservationTime(id);
    }
}
