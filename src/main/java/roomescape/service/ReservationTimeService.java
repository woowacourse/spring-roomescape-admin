package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.ReservationTime;

@Component
public class ReservationTimeService {

    private static final int EXIST_RESERVATION_TIME = -1;

    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationTimeService(final ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDAO.findAll();
    }

    public long addReservationTime(final ReservationTime reservationTime) {
        if (reservationTimeDAO.existsByStartAt(reservationTime.getStartAt())) {
            return EXIST_RESERVATION_TIME;
        }
        return reservationTimeDAO.insert(reservationTime);
    }

    public boolean deleteById(final long id) {
        return reservationTimeDAO.deleteById(id);
    }
}
