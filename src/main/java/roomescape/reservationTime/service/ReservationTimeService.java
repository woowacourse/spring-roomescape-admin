package roomescape.reservationTime.service;

import org.springframework.stereotype.Service;
import roomescape.common.Dao;
import roomescape.reservationTime.domain.ReservationTime;
import roomescape.reservationTime.dto.AllReservationTimeResponse;
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

    public AllReservationTimeResponse findAll() {
        return AllReservationTimeResponse.from(reservationTimeDao.findAll());
    }

    public void deleteById(Long id) {
        reservationTimeDao.deleteById(id);
    }
}
