package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.request.ReservationTimeCreateRequest;
import roomescape.controller.dto.response.ReservationTimeResponse;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponse> getReservationTimes() {
        return reservationTimeDao.findAll().stream()
                .map(ReservationTimeResponse::fromReservationTime)
                .toList();
    }

    public ReservationTimeResponse createReservationTime(ReservationTimeCreateRequest reservationTimeCreateRequest) {
        ReservationTime reservationTime = reservationTimeCreateRequest.toReservationTime();
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);
        return ReservationTimeResponse.fromReservationTime(savedReservationTime);
    }

    public void deleteReservationTime(Long reservationTimeId) {
        reservationTimeDao.delete(reservationTimeId);
    }
}
