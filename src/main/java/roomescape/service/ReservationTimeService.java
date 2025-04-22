package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.ReservationTime;

@Component
public class ReservationTimeService {

    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationTimeService(final ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDAO.findAll();
    }

}
