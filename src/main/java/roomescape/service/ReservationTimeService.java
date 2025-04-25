package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.reservationtime.request.ReservationTimeRequest;
import roomescape.controller.reservationtime.response.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeDao;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponse save(final ReservationTimeRequest reservationTimeRequest) {
        ReservationTime time = reservationTimeRequest.toTime();
        long id = reservationTimeDao.save(time);
        return ReservationTimeResponse.from(id, time);
    }

    public List<ReservationTimeResponse> read() {
        List<ReservationTime> times = reservationTimeDao.read();
        return times.stream()
                .map(ReservationTimeResponse::of)
                .toList();
    }

    public void delete(final Long id) {
        reservationTimeDao.delete(id);
    }
}
