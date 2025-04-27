package roomescape.service.reservationtime;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeDao;
import roomescape.service.reservationtime.request.ReservationTimeServiceRequest;
import roomescape.service.reservationtime.response.ReservationTimeResponse;

@Service
public final class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponse save(final ReservationTimeServiceRequest reservationTimeRequest) {
        final ReservationTime time = reservationTimeRequest.toTime();
        final long id = reservationTimeDao.save(time);
        return ReservationTimeResponse.from(id, time);
    }

    public List<ReservationTimeResponse> findAll() {
        final List<ReservationTime> times = reservationTimeDao.findAll();
        return times.stream()
                .map(ReservationTimeResponse::of)
                .toList();
    }

    public void deleteById(final Long id) {
        reservationTimeDao.deleteById(id);
    }
}
