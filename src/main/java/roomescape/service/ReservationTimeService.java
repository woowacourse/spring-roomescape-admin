package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeDAO;

@Service
public class ReservationTimeService {

    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationTimeService(ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimeDAO.selectAllReservationTimes();
    }

    public ReservationTime addReservationTime(ReservationTime reservationTime) {
        return reservationTimeDAO.insertReservationTime(reservationTime);
    }

    public ReservationTime findReservationTime(long id) {
        return reservationTimeDAO.selectReservationById(id);
    }

    public void removeReservationTime(long id) {
        reservationTimeDAO.deleteReservationTime(id);
    }
}
