package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.ReservationTime;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationTimeService(ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public ReservationTime create(ReservationTime reservationTime) {
        Long id = reservationTimeDAO.insertWithKeyHolder(reservationTime);
        return reservationTimeDAO.findReservationTimeById(id);
    }

    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimeDAO.findAllReservationTimes();
    }

    public void delete(Long id) {
        reservationTimeDAO.delete(id);
    }
}
