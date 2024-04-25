package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@Service
public class ReservationTimeService {
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationTimeService(final ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public ReservationTime save(final ReservationTimeRequest reservationTimeRequest) {
        final ReservationTime reservationTime = new ReservationTime(reservationTimeRequest.startAt());
        return reservationTimeDAO.insert(reservationTime);
    }
}
