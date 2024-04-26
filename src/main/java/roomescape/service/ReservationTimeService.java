package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.request.ReservationTimeRequest;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeDAO;

@Service
public class ReservationTimeService {

    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationTimeService(ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimeDAO.findAllReservationTimes();
    }

    public ReservationTime addReservationTime(ReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(request.startAt());
        return reservationTimeDAO.addReservationTime(reservationTime);
    }

    public ReservationTime findReservationTime(long id) {
        return reservationTimeDAO.findReservationById(id);
    }

    public void deleteReservationTime(long id) {
        reservationTimeDAO.deleteReservationTime(id);
    }
}
