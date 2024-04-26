package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

@Component
public class ReservationTimeJdbcRepository implements ReservationTimeRepository {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeJdbcRepository(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @Override
    public void saveReservationTime(ReservationTime reservationTime) {
        reservationTimeDao.save(reservationTime);
    }

    @Override
    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimeDao.findAll();
    }

    @Override
    public void deleteReservationTimeById(long reservationTimeId) {
        reservationTimeDao.deleteById(reservationTimeId);
    }
}
