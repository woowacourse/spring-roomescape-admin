package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.TimeDao;

@Repository
public class ReservationTimeService {
    private final TimeDao timeDao;

    public ReservationTimeService(final TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public ReservationTimeResponse saveTime(final ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = timeDao.save(reservationTimeRequest);
        return ReservationTimeResponse.from(reservationTime);
    }

    public List<ReservationTime> getTimes() {
        return timeDao.getAll();
    }

    public void deleteTime(final long id) {
        timeDao.delete(id);
    }
}
