package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDaoInterface;
import roomescape.entity.ReservationTime;

@Service
public class ReservationTimeService {

    private final ReservationTimeDaoInterface reservationTimeDao;

    public ReservationTimeService(ReservationTimeDaoInterface reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTime addReservation(ReservationTime reservationTime) {
        return reservationTimeDao.addReservation(reservationTime);
    }

    public List<ReservationTime> getTimeReservations() {
        return reservationTimeDao.getTimeReservations();
    }

    public int deleteTimeReservationById(Long id) {
        return reservationTimeDao.deleteTimeReservation(id);
    }
}
