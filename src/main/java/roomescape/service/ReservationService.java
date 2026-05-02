package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public Reservation save(Reservation reservation) {
        ReservationTime time = reservationDao.findTimeById(reservation.getTime().getId());
        return reservationDao.save(reservation, time);
    }

    public void delete(Long id) {
        reservationDao.delete(id);
    }
}
