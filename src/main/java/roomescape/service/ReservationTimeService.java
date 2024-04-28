package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationTimeRequest;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> read() {
        return reservationTimeDao.findAll();
    }

    public ReservationTime create(ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeDao.insert(reservationTimeRequest);
    }

    public void delete(long id) {
        reservationTimeDao.delete(id);
    }

    public ReservationTime findById(ReservationRequest reservationRequest) {
        return reservationTimeDao.findById(reservationRequest.timeId());
    }
}
