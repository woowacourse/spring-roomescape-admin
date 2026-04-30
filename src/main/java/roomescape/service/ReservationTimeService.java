package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.ReservationTime;

@Service
public class ReservationTimeService {

    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationTimeService(ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public ReservationTime create(ReservationTime reservationTime) {
        return reservationTimeDAO.insert(reservationTime);
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDAO.findAll();
    }

    public void delete(Long id) {
        reservationTimeDAO.delete(id);
    }
}
