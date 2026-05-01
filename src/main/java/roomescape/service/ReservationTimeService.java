package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

@Service
@Transactional(readOnly = true)
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> getReservationTimes() {
        return reservationTimeDao.findAllReservationTimes();
    }

    @Transactional
    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        Long id = reservationTimeDao.insertWithKeyHolder(reservationTime);
        return ReservationTime.withId(id, reservationTime);
    }

    @Transactional
    public int deleteReservationTime(Long id) {
        return reservationTimeDao.delete(id);
    }
}
