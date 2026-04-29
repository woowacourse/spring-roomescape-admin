package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationDao;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Repository
public class ReservationRepository {
    private final ReservationDao reservationDao;

    public ReservationRepository(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public Reservation save(String name, String date, ReservationTime time) {
        return reservationDao.save(name, date, time);
    }

    public void delete(Long id) {
        reservationDao.delete(id);
    }
}
