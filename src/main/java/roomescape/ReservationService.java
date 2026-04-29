package roomescape;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationTimeDao reservationTimeDao;
    private final ReservationDao reservationDao;
    public ReservationService(ReservationTimeDao reservationTimeDao, ReservationDao reservationDao) {
        this.reservationDao=reservationDao;
        this.reservationTimeDao=reservationTimeDao;
    }

    public List<Reservation> selectReservations() {
        return reservationDao.selectReservations();
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = reservationTimeDao.selectById(reservationRequest.getTimeId());
        return reservationDao.insertReservation(reservationRequest, reservationTime);
    }

    public void deleteReservation(long id) {
        reservationDao.deleteReservation(id);
    }
}
