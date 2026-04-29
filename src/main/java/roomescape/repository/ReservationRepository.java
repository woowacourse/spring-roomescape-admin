package roomescape.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;

@Repository
public class ReservationRepository {
    private final ReservationDao reservationDao;

    public ReservationRepository(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Optional<Reservation> getReservation(long id) {
        return reservationDao.getReservation(id);
    }

    public List<Reservation> getAllReservation() {
        return List.copyOf(reservationDao.getAllReservation());
    }

    public Reservation addReservation(Reservation reservation) {
        return reservationDao.insertReservation(reservation);
    }

    public void deleteReservation(long id) {
        reservationDao.deleteReservation(id);
    }
}
