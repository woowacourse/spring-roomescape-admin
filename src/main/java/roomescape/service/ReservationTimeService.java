package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> readAll() {
        return reservationTimeDao.selectAll();
    }

    public ReservationTime register(LocalTime startAt) {
        return reservationTimeDao.insert(new ReservationTime(startAt));
    }

    public void deregister(Long id) {
        boolean isDeleted = reservationTimeDao.delete(id);
        if (!isDeleted) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 id 이므로, ReservationTime을 삭제할 수 없습니다.");
        }
    }

}
