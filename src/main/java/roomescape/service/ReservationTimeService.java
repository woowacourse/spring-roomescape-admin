package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.ReservationTime;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        return reservationTimes.stream()
                .map(reservationTime -> new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt()))
                .toList();
    }

    public long save(ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeDao.save(reservationTimeRequest);
    }

    public void deleteById(long id) {
        reservationTimeDao.deleteById(id);
    }
}
