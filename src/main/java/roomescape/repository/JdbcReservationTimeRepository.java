package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final ReservationTimeDao reservationTimeDao;

    public JdbcReservationTimeRepository(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    @Override
    public ReservationTime findById(long id) {
        return reservationTimeDao.findById(id);
    }

    @Override
    public long save(LocalTime startAt) {
        return reservationTimeDao.insert(startAt);
    }

    @Override
    public void deleteById(long id) {
        reservationTimeDao.deleteById(id);
    }
}
