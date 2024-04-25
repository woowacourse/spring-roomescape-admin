package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

import java.util.List;

@Service
public class ReservationTimeService {

    @Autowired
    ReservationTimeDao reservationTimeDao;

    public ReservationTimeResponse create(ReservationTimeRequest request) {
        ReservationTime reservationTime = request.toEntity();
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
