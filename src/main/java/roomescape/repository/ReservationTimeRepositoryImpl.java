package roomescape.repository;

import org.springframework.stereotype.Component;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimes;

@Component
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeRepositoryImpl(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimes findAll() {
        return new ReservationTimes(reservationTimeDao.findAll());
    }

    public ReservationTime findById(Long id) {
        return reservationTimeDao.findById(id);
    }

    public ReservationTime save(ReservationTime reservationTime) {
        return reservationTimeDao.save(reservationTime);
    }

    public boolean deleteById(Long id) {
        return reservationTimeDao.deleteById(id);
    }
}
