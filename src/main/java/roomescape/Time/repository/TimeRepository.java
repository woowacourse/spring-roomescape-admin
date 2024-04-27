package roomescape.Time.repository;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.Time.dao.TimeDao;
import roomescape.Time.domain.Time;

@Component
public class TimeRepository {
    private final TimeDao timeDao;

    public TimeRepository(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public void saveReservationTime(Time reservationTime) {
        timeDao.save(reservationTime);
    }

    public List<Time> findAllReservationTimes() {
        return timeDao.findAll();
    }

    public void deleteReservationTimeById(long reservationTimeId) {
        timeDao.deleteById(reservationTimeId);
    }
}
