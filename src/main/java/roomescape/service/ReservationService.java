package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dao.ReservationDao;
import roomescape.ReservationReq;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Reservation createReservation(ReservationReq reservationReq) {
        Long id = reservationDao.insertWithKeyHolder(reservationReq);
        return reservationDao.findReservationById(id);
    }

    public List<Reservation> getReservations() {
        return reservationDao.findAllReservations();
    }

    public void deleteReservation(Long id) {
        reservationDao.delete(id);
    }
}
