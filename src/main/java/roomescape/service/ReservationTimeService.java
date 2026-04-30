package roomescape.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll().stream()
                .map(this::toReservationTime)
                .toList();
    }

    public ReservationTime create(final String startAt) {
        final long id = reservationTimeDao.save(startAt);
        return ReservationTime.create(id, startAt);
    }

    public void delete(final long id) {
        reservationTimeDao.delete(id);
    }

    private ReservationTime toReservationTime(final Map<String, Object> row) {
        return ReservationTime.create(
                ((Number) row.get("id")).longValue(),
                row.get("start_at").toString()
        );
    }
}
