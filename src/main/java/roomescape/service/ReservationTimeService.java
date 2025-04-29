package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.reservation.ReservationTime;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponse save(ReservationTimeRequest reservationTimeRequest) {
        Long timeId = reservationTimeDao.save(reservationTimeRequest.toReservationTime());
        return new ReservationTimeResponse(timeId, reservationTimeRequest.startAt());
    }

    public ReservationTimeResponse findById(Long id) {
        ReservationTime reservationTime = reservationTimeDao.findById(id);
        return ReservationTimeResponse.from(reservationTime);
    }

    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public void deleteById(Long id) {
        reservationTimeDao.deleteById(id);
    }
}
