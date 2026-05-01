package roomescape.reservationtime;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

    int delete(long id) {
        return reservationTimeDao.delete(id);
    }

    Optional<ReservationTime> findById(long id) {
        return reservationTimeDao.findById(id);
    }
}
