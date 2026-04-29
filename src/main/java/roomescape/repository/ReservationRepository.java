package roomescape.repository;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationCommand;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationRepository {
    private final ReservationDao reservationDao;

    public ReservationRepository(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> getAllReservation() {
        return Collections.unmodifiableList(reservationDao.getAllReservation());
    }

    public Reservation addReservation(ReservationCommand reservationCommand, ReservationTime reservationTime) {
        long id = reservationDao.insertReservation(reservationCommand);
        return new Reservation(id, reservationCommand.name(), reservationCommand.date(), reservationTime);
    }

    public int deleteReservation(long id) {
        return reservationDao.deleteReservation(id);
    }
}
