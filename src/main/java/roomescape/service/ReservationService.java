package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.entity.Reservation;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationDao.createReservation(reservation);
    }

    public List<Reservation> findReservations() {
        return reservationDao.findReservations();
    }

    public void removeReservation(Long id) {
        reservationDao.removeReservation(id);
    }
}
