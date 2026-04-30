package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    @Autowired
    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponse> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeDao.getTimes();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::of)
                .toList();
    }

    public ReservationTimeResponse createReservationTime(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeDao.create(reservationTimeRequest);
        return ReservationTimeResponse.of(reservationTime);
    }

    public void deleteReservationTime(Long id) {
        reservationTimeDao.delete(id);
    }
}
