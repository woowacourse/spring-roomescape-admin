package roomescape.reservationtime;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
class ReservationTimeRepository {
    private final ReservationTimeDao reservationTimeDao;

    ReservationTimeRepository(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    ReservationTime save(LocalTime startAt) {
        return reservationTimeDao.save(startAt);
    }

    void delete(Long id) {
        reservationTimeDao.delete(id);
    }

    ReservationTime findById(Long id) {
        return reservationTimeDao.findById(id);
    }
}
