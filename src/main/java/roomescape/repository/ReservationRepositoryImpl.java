package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.Reservation;
import roomescape.dao.ReservationDao;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {
    private final ReservationDao reservationDao;

    public ReservationRepositoryImpl(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public long add(Reservation reservation) {
        return reservationDao.save(reservation);
    }

    @Override
    public List<Reservation> getAll() {
        return reservationDao.findAll();
    }

    @Override
    public void remove(long id) {
        reservationDao.delete(id);
    }
}
