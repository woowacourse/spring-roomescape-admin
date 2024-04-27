package roomescape.reservation.domain.repository;

import org.springframework.stereotype.Repository;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;

import java.util.List;

@Repository
public class ReservationRepository {

    private final ReservationDao reservationDao;

    public ReservationRepository(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Reservation insert(Reservation reservation) {
        return reservationDao.insert(reservation);
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public int deleteById(Long id) {
        return reservationDao.deleteById(id);
    }
}
