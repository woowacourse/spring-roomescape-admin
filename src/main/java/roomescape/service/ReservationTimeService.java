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

    public List<ReservationTime> findAllReservationTime() {
        return reservationTimeDAO.findAllReservationTime();
    }

    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        Long generatedId = reservationTimeDAO.insertWithKeyHolder(reservationTime);
        return new ReservationTime(generatedId, reservationTime.getStartAt());
    }

    public void deleteReservationTime(Long id) {
        reservationTimeDAO.delete(id);
    }
}
