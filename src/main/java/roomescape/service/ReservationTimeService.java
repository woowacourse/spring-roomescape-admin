package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.ReservationTimeDao;

@Repository
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponse saveTime(final ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeDao.save(reservationTimeRequest);
        return ReservationTimeResponse.from(reservationTime);
    }

    public List<ReservationTime> getTimes() {
        return reservationTimeDao.getAll();
    }

    public void deleteTime(final long id) {
        reservationTimeDao.delete(id);
    }
}
