package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.CreateReservationTimeRequest;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

import java.util.List;

@Service
public class TimeService {

    private final ReservationTimeDao reservationTimeDao;

    public TimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> readAll() {
        return reservationTimeDao.readAll();
    }

    public ReservationTime createTime(CreateReservationTimeRequest request) {
        int createdId = reservationTimeDao.create(request);
        return new ReservationTime(createdId, request.startAt());
    }

    public void deleteTime(int id) {
        reservationTimeDao.delete(id);
    }
}
