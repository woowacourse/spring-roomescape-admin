package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.db.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;


@Service
public class ReservationTimeService {
    @Autowired
    ReservationTimeDao reservationTimeDao;

    public Long create(ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeDao.create(reservationTimeRequest);
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    public ReservationTime findById(final Long id) {
        return reservationTimeDao.findById(id);
    }

    public void deleteById(final long id) {
        reservationTimeDao.deleteById(id);
    }
}
