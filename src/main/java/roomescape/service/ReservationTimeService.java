package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.entity.ReservationTime;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
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
