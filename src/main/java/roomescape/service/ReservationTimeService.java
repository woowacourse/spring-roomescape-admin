package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao){
        this.reservationTimeDao = reservationTimeDao;
    }

    @Transactional
    public ReservationTime create(String startAt) {
        return reservationTimeDao.create(startAt);
    }

    @Transactional(readOnly = true)
    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    @Transactional
    public void delete(Long id){
        reservationTimeDao.delete(id);
    }
}
