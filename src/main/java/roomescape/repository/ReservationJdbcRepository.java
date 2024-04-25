package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.dao.ReservationDaoImpl;
import roomescape.domain.Reservation;

@Component
public class ReservationJdbcRepository implements ReservationRepository {
    private final ReservationDaoImpl reservationDao;

    public ReservationJdbcRepository(ReservationDaoImpl reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public Reservation saveReservation(Reservation reservation) {
        return reservationDao.save(reservation);
    }

    @Override
    public List<Reservation> findAllReservation() {
        return reservationDao.findAll();
    }

    @Override
    public void deleteReservationById(long reservationId) {
        reservationDao.deleteById(reservationId);
    }
}
