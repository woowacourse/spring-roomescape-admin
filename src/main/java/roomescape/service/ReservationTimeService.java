package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

@Service
@Transactional
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @Transactional(readOnly = true)
    public List<ReservationTime> getReservationTimes() {
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
