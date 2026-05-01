package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.ReservationReq;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;

@Service
@Transactional
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Reservation createReservation(ReservationReq reservationReq) {
        Long id = reservationDao.insertWithKeyHolder(reservationReq);
        return reservationDao.findReservationById(id);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getReservations() {
        return reservationDao.findAllReservations();
    }

    public int deleteReservation(Long id) {
        return reservationDao.delete(id);
    }
}
