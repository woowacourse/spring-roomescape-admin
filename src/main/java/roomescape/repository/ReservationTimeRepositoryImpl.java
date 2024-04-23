package roomescape.repository;

import java.util.List;
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
    public List<ReservationTime> getAll() {
        return this.reservationTimeDao.findAll();
    }

    @Override
    public ReservationTime get(long id) {
        return this.reservationTimeDao.findOne(id);
    }

    @Override
    public long add(ReservationTime time) {
        return reservationTimeDao.save(time);
    }

    @Override
    public void remove(final long id) {
        reservationTimeDao.delete(id);
    }
}
