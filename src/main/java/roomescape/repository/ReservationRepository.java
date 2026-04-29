package roomescape.repository;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;

@Repository
public class ReservationRepository {
    private final ReservationDao reservationDao;

    public ReservationRepository(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> getAllReservation() {
        return Collections.unmodifiableList(reservationDao.getAllReservation());
    }

    public Reservation addReservation(Reservation reservation) {
        return reservationDao.insertReservation(reservation);
    }

    public int deleteReservation(long id) {
        return reservationDao.deleteReservation(id);
    }
}
