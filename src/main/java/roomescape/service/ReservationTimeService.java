package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.ReservationTime;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;

@Service
public class ReservationTimeService {

    private ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    public ReservationTime findById(long id) {
        return reservationTimeDao.findById(id);
    }

    public int save(ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeDao.save(reservationTimeRequest);
    }

    public void deleteById(long id) {
        reservationTimeDao.deleteById(id);
    }
}
