package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeDao;

@Service
@Transactional
public class ReservationTimeServiceImpl implements ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeServiceImpl(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @Override
    public ReservationTime save(String startAt) {
        return reservationTimeDao.save(
                ReservationTime.constructWithoutId(startAt)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    @Override
    public void deleteById(Long targetId) {
        reservationTimeDao.delete(targetId);
    }
}
