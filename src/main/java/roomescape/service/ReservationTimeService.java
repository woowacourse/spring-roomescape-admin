package roomescape.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.ReservationTime;

@Service
public class ReservationTimeService {

    private static final int DUPLICATED_RESERVATION_TIME = -1;

    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationTimeService(final ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public Optional<ReservationTime> findById(long id) {
        return reservationTimeDAO.findById(id);
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDAO.findAll();
    }

    public long addReservationTime(final ReservationTime reservationTime) {
        if (reservationTimeDAO.existsByStartAt(reservationTime.getStartAt())) {
            return DUPLICATED_RESERVATION_TIME;
        }
        return reservationTimeDAO.insert(reservationTime);
    }

    public boolean deleteById(final long id) {
        return reservationTimeDAO.deleteById(id);
    }
}
