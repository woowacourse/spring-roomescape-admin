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
}
