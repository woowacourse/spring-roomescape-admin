package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationDao;

    public ReservationService(ReservationRepository reservationDao) {
        this.reservationDao = reservationDao;
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteReservation(id);
    }

    public long addReservation(Reservation newReservation) {
        return reservationDao.addReservation(newReservation);
    }

    public List<Reservation> allReservations() {
        return reservationDao.findAllReservations();
    }
}
