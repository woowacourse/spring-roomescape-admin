package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Time;

@Component
public class ReservationTimeRepository {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeRepository(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public void saveReservationTime(Time reservationTime) {
        reservationTimeDao.save(reservationTime);
    }

    public List<Time> findAllReservationTimes() {
        return reservationTimeDao.findAll();
    }

    public void deleteReservationTimeById(long reservationTimeId) {
        reservationTimeDao.deleteById(reservationTimeId);
    }
}
