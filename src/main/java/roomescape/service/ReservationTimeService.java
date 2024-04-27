package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.repository.web.dao.ReservationTimeDao;
import roomescape.domain.reservation.ReservationTime;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTime addTime(ReservationTime reservationTime) {
        return reservationTimeDao.add(reservationTime);
    }

    public List<ReservationTime> allReservationTimes() {
        return reservationTimeDao.findAll();
    }

    public void delete(Long id) {
        reservationTimeDao.delete(id);
    }
}
