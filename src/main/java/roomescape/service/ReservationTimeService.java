package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dao.ReservationTimeDao;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> getRservationTimes() {
        return reservationTimeDao.findAllReservationTimes();
    }

    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        Long id = reservationTimeDao.insertWithKeyHolder(reservationTime);
        return ReservationTime.withId(id, reservationTime);
    }

    public void deleteReservationTime(Long id) {
        reservationTimeDao.delete(id);
    }
}
