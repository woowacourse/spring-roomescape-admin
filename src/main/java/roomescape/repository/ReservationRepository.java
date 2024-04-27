package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.dao.ReservationDaoImpl;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Component
public class ReservationRepository {
    private final ReservationDaoImpl reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationRepository(ReservationDaoImpl reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public void saveReservation(Reservation reservation) {
        Time time = reservationTimeDao.findById(reservation.getReservationTime().getId());
        reservation.setTime(time);
        reservationDao.save(reservation);
    }

    public List<Reservation> findAllReservation() {
        return reservationDao.findAll();
    }

    public void deleteReservationById(long reservationId) {
        reservationDao.deleteById(reservationId);
    }
}
