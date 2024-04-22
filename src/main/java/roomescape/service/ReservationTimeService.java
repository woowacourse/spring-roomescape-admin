package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponse createReservationTime(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime time = reservationTimeRequest.toTime();
        long id = reservationTimeDao.create(time);
        return ReservationTimeResponse.toResponse(id, reservationTimeRequest);
    }

    public List<ReservationTimeResponse> getAllReservationTimes() {
        List<ReservationTime> times = reservationTimeDao.getAll();
        return ReservationTimeResponse.toResponses(times);
    }

    public void deleteReservationTime(long id) {
        reservationTimeDao.delete(id);
    }
}
