package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationTimeDao;
import roomescape.model.ReservationTime;

@Repository
public class ReservationTimeRepository {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeRepository(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    public ReservationTime save(String startAt) {
        return reservationTimeDao.save(startAt);
    }

    public void delete(Long id) {
        reservationTimeDao.delete(id);
    }
}
