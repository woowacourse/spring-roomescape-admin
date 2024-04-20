package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.dto.Reservation;
import roomescape.dto.ReservationRequest;

@Repository
public class ReservationRepository {
    private final ReservationDao reservationDao;

    public ReservationRepository(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Reservation saveReservation(final ReservationRequest reservationRequest) {
        return reservationDao.save(reservationRequest);
    }

    /*
    public void deleteReservation(final long id) {
        reservations.remove(id);
    }

    public void deleteAll() {
        reservations.clear();
    }*/

    public List<Reservation> getAllReservations() {
        return reservationDao.getAll();
    }
}
