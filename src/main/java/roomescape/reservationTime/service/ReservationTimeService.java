package roomescape.reservationTime.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.common.Dao;
import roomescape.reservationTime.domain.ReservationTime;
import roomescape.reservationTime.dto.ReservationTimeRequest;
import roomescape.reservationTime.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {
    private final Dao<ReservationTime> reservationTimeDao;

    public ReservationTimeService(Dao<ReservationTime> reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponse add(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime newReservationTime = reservationTimeRequest.createReservationTime();
        ReservationTime savedReservationTime = reservationTimeDao.add(newReservationTime);
        return ReservationTimeResponse.from(savedReservationTime);
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimeDao.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public void deleteById(Long id) {
        reservationTimeDao.deleteById(id);
    }
}
