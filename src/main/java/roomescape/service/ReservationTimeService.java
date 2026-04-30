package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> getTimes() {
        return reservationTimeDao.getTimes();
    }

    public ReservationTime createTime(ReservationTimeRequest request) {
        final Long id = reservationTimeDao.insertAndGetId(request);
        return new ReservationTime(id, request.startAt());
    }

    public void deleteTime(Long id) {
        reservationTimeDao.delete(id);
    }
}
