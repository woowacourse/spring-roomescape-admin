package roomescape.reservationTime.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.common.Dao;
import roomescape.reservationTime.ReservationTime;
import roomescape.reservationTime.dto.ReservationTimeRequest;

@Service
public class ReservationTimeService {
    private final Dao<ReservationTime> reservationTimeDao;

    public ReservationTimeService(Dao<ReservationTime> reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTime add(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime newReservationTime = reservationTimeRequest.createReservationTime();
        return reservationTimeDao.add(newReservationTime);
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    public void deleteById(Long id) {
        reservationTimeDao.deleteById(id);
    }
}
