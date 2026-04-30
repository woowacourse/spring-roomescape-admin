package roomescape.service;

import java.time.LocalTime;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.repository.ReservationTimeDao;

@Service
public class ReservationTimeCommandService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeCommandService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponse create(LocalTime startAt) {
        ReservationTime savedReservationTime = reservationTimeDao.save(ReservationTime.pending(startAt));
        return ReservationTimeResponse.from(savedReservationTime);
    }

    public void delete(long reservationTimeId) {
        reservationTimeDao.deleteByTimeId(reservationTimeId);
    }
}
