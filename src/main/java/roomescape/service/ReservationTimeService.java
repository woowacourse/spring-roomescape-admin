package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTime create(ReservationTime reservationTime) {
        return reservationTimeDao.save(reservationTime);
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    public ReservationTime findById(Long id) {
        return reservationTimeDao.findById(id);
    }

    public void deleteById(Long id) {
        reservationTimeDao.deleteById(id);
    }
}
