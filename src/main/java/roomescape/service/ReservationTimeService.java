package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public long add(final ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeDao.add(reservationTimeRequest);
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    public int delete(final long id) {
        return reservationTimeDao.delete(id);
    }
}
