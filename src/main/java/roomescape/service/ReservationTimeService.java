package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

import java.util.List;

@Service
public class ReservationTimeService {

    @Autowired
    ReservationTimeDao reservationTimeDao;

    public ReservationTime create(ReservationTime request) {
        Long id = reservationTimeDao.save(request);
        return ReservationTime.toEntity(id, request);
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.readAll();
    }

    public void deleteTime(Long id) {
        reservationTimeDao.delete(id);
    }
}
