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

    public List<ReservationTime> getReservations() {
        return reservationTimeDAO.findAllReservations();
    }

    public long addReservationTime(ReservationTime reservationTime) {
        return reservationTimeDAO.addReservationTime(reservationTime);
    }

    public ReservationTime getReservationTime(long id) {
        return reservationTimeDAO.findReservationTime(id);
    }

    public void removeReservationTime(long id) {
        reservationTimeDAO.deleteReservationTime(id);
    }
}
