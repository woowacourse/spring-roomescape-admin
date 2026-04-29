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

    public ReservationTime enrollReservationTimes(String startAt) {
        return reservationTimeDao.save(
                ReservationTime.constructWithoutId(startAt)
        );
    }

    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimeDao.findAll();
    }

    public void deleteSpecificReservationTime(Long targetId) {
        reservationTimeDao.delete(targetId);
    }
}
