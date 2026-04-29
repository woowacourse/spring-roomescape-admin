package roomescape.reservation;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.reservationtime.ReservationTime;

@Repository
class ReservationRepository {
    private final ReservationDao reservationDao;

    ReservationRepository(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    Reservation save(String name, LocalDate date, ReservationTime time) {
        return reservationDao.save(name, date, time);
    }

    void delete(Long id) {
        reservationDao.delete(id);
    }
}
