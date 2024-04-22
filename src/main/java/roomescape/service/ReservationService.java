package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationDao;

@Repository
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Reservation saveReservation(final ReservationRequest reservationRequest) {
        long reservationId = reservationDao.save(reservationRequest);
        return reservationDao.get(reservationId);
    }

    public List<Reservation> getAllReservations() {
        return reservationDao.getAll();
    }

    public void deleteReservation(final long id) {
        reservationDao.delete(id);
    }
}
