package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.ReservationReq;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> getReservations() {
        return reservationDao.findAllReservations();
    }

    @Transactional
    public Reservation createReservation(ReservationReq reservationReq) {
        Long id = reservationDao.insertWithKeyHolder(reservationReq);
        return reservationDao.findReservationById(id);
    }

    @Transactional
    public int deleteReservation(Long id) {
        return reservationDao.delete(id);
    }
}
