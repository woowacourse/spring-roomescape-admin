package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeDao;
import roomescape.service.dto.reservationtime.request.ReservationTimeRequest;
import roomescape.service.dto.reservationtime.response.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponse save(final ReservationTimeRequest reservationTimeRequest) {
        final ReservationTime time = reservationTimeRequest.toTime();
        final long id = reservationTimeDao.save(time);
        return ReservationTimeResponse.from(id, time);
    }

    public List<ReservationTimeResponse> readAll() {
        final List<ReservationTime> times = reservationTimeDao.readAll();
        return times.stream()
                .map(ReservationTimeResponse::of)
                .toList();
    }

    public void deleteById(final Long id) {
        reservationTimeDao.deleteById(id);
    }
}
