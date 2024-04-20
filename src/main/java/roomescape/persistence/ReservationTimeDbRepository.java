package roomescape.persistence;

import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDbRepository implements ReservationTimeRepository {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeDbRepository(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        Long id = reservationTimeDao.insert(reservationTime);
        return new ReservationTime(id, reservationTime);
    }
}
