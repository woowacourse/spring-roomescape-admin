package roomescape.repository;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation.Reservation;
import roomescape.domain.Reservation.ReservationCommand;
import roomescape.domain.ReservationTime.ReservationTime;

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

    public void deleteReservation(long id) {
        reservationDao.deleteReservation(id);
    }
}
