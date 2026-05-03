package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.List;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final ReservationDao reservationDao;

    public JdbcReservationRepository(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public List<Reservation> findAllJoinedDto() {
        return reservationDao.findAll();
    }

    @Override
    public Reservation findById(long id) {
        return reservationDao.findById(id);
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationDao.insert(reservation);
    }

    @Override
    public void deleteById(long id) {
        reservationDao.deleteById(id);
    }
}
