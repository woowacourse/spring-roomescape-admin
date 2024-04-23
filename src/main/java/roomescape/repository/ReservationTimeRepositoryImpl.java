package roomescape.repository;

import org.springframework.stereotype.Component;
import roomescape.dao.ReservationTimeDao;
import roomescape.data.vo.ReservationTime;

@Component
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeRepositoryImpl(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @Override
    public long add(ReservationTime time) {
        return reservationTimeDao.save(time);
    }
}
