package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@Repository
public class TimeRepository {
    private final TimeDao timeDao;

    public TimeRepository(final TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public ReservationTime saveTime(final ReservationTimeRequest reservationTimeRequest) {
        return timeDao.save(reservationTimeRequest);
    }

    public List<ReservationTime> getTimes() {
        return timeDao.getAll();
    }

    public void deleteTime(final long id) {
        timeDao.delete(id);
    }
}
