package roomescape.reservation.repository;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.Time.dao.TimeDao;
import roomescape.Time.domain.Time;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;

@Component
public class ReservationRepository {
    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationRepository(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public void saveReservation(Reservation reservation) {
        Time time = timeDao.findById(reservation.getReservationTime().getId());
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
