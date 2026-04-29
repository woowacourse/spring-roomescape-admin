package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.dao.ReservationDao;
import roomescape.ReservationReq;

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

    public void deleteReservation(Long id) {
        reservationDao.delete(id);
    }
}
