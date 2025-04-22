package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponse createReservationTime(
            final CreateReservationTimeRequest createReservationTimeRequest
    ) {
        ReservationTime reservationTime = reservationTimeDao.createTime(createReservationTimeRequest.startAt());
        return ReservationTimeResponse.from(reservationTime);
    }

    public List<ReservationTimeResponse> findAllReservationTime() {
        return ReservationTimeResponse.from(reservationTimeDao.findAllTimes());
    }

    public void deleteReservationById(final Long id) {
        reservationTimeDao.deleteTimeById(id);
    }
}
