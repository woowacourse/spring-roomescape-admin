package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.db.ReservationDao;
import roomescape.db.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;


@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;
    private final ReservationDao reservationDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao, final ReservationDao reservationDao) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationDao = reservationDao;
    }

    public ReservationTime create(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = new ReservationTime(reservationTimeRequest.startAt());
        return reservationTimeDao.save(reservationTime);
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    public void deleteById(final long id) {
        reservationTimeDao.deleteById(id);
    }
}
