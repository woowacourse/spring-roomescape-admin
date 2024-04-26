package roomescape.reservation.domain.repository;

import org.springframework.stereotype.Repository;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;

import java.util.List;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationDao reservationDao;

    public ReservationRepositoryImpl(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public Reservation insert(Reservation reservation) {
        return reservationDao.insert(reservation);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    @Override
    public int deleteById(Long id) {
        return reservationDao.deleteById(id);
    }
}
