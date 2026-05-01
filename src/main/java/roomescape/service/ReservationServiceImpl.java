package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationServiceImpl(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @Override
    public Reservation save(String name, String date, Long timeId) {
        ReservationTime targetTime = reservationTimeDao.findById(timeId);
        return reservationDao.save(
                Reservation.constructWithNoId(name, date, targetTime)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    @Override
    public void deleteById(Long targetId) {
        reservationDao.delete(targetId);
    }
}
