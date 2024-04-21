package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeDao;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao timeDao;

    public ReservationTimeService(ReservationTimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public ReservationTime addReservationTime(ReservationTime reservationTime) {
        return timeDao.add(reservationTime);
    }

    public List<ReservationTime> findAllReservationTimes() {
        return timeDao.findAll();
    }

    public void deleteReservationTimeById(Long id) {
        timeDao.deleteById(id);
    }
}
