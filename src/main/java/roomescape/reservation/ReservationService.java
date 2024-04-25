package roomescape.reservation;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.time.ReservationTime;
import roomescape.time.ReservationTimeDao;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public Reservation save(ReservationRequest request) {
        ReservationTime reservationTime = reservationTimeDao.findById(request.timeId());
        Reservation result = new Reservation(request.name(), request.date(), reservationTime);
        return reservationDao.save(result);
    }

    public void delete(long id) {
        reservationDao.deleteById(id);
    }
}
