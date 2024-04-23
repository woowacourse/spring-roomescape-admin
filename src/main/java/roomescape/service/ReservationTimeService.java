package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.reservation.ReservationTime;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTime addTime(ReservationTime reservationTime) {
        Long savedId = reservationTimeDao.add(reservationTime);
        return reservationTimeDao.findById(savedId);
    }

    public List<ReservationTime> allReservationTimes() {
        return reservationTimeDao.findAllTimes();
    }

    public void delete(Long id) {
        reservationTimeDao.delete(id);
    }
}
