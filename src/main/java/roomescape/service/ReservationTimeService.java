package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeDao;

@Service
@Transactional
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTime add(String startAt) {
        return reservationTimeDao.save(
                ReservationTime.constructWithoutId(startAt)
        );
    }

    public List<ReservationTime> find() {
        return reservationTimeDao.findAll();
    }

    public void delete(Long targetId) {
        reservationTimeDao.delete(targetId);
    }
}
