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

    public Reservation save(String name, String date, Long timeId) {
        ReservationTime reservationTime = reservationTimeDao.findById(timeId);
        Long reservationId = reservationDao.save(name, date, timeId);
        return new Reservation(reservationId, name, date, reservationTime);
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public void delete(Long reservationId) {
        reservationDao.deleteById(reservationId);
    }
}
