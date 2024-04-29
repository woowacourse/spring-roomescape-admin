package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponse create(ReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(request.getStartAt());
        Long id = reservationTimeDao.save(reservationTime);
        return ReservationTimeResponse.toResponse(id, reservationTime);
    }

    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeDao.readAll();
        return ReservationTimeResponse.toReservationTimesResponse(reservationTimes);
    }

    public void deleteTime(Long id) {
        reservationTimeDao.delete(id);
    }
}
