package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationTimeCreateRequest;

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

    public ReservationTime create(ReservationTimeCreateRequest reservationTimeCreateRequest) {
        return reservationTimeDao.insert(reservationTimeCreateRequest);
    }

    public void delete(long id) {
        reservationTimeDao.delete(id);
    }

    public ReservationTime findById(ReservationCreateRequest reservationCreateRequest) {
        return reservationTimeDao.findById(reservationCreateRequest.timeId());
    }
}
