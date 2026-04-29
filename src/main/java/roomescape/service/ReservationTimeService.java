package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.dao.ReservationTimeDao;

@Service
@Transactional
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @Transactional(readOnly = true)
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
