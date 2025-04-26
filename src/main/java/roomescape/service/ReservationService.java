package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public Reservation save(Reservation reservation) {
        ReservationTime reservationTime = reservationTimeDao.findById(reservation.getTime().getId());
        Long reservationId = reservationDao.save(reservation);
        return new Reservation(reservationId, reservation.getName(), reservation.getDate(), reservationTime);
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public ReservationTime findReservationTime(Long timeId) {
        return reservationTimeDao.findById(timeId);
    }

    public void delete(Long reservationId) {
        reservationDao.deleteById(reservationId);
    }
}
