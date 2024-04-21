package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationDao;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Reservation addReservation(Reservation reservation) {
        return reservationDao.add(reservation);
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.findAll();
    }

    public void deleteReservationById(long reservationId) {
        reservationDao.deleteById(reservationId);
    }
}
