package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

import java.util.List;

@Service
public class ReservationTimeService {

    private ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    public ReservationTime create(String startAt) {
        Long id = reservationTimeDao.insert(new ReservationTime(null, startAt));
        return reservationTimeDao.findBy(id);
    }

    public void delete(Long id) {
        validateId(id);
        int deletedCount = reservationTimeDao.delete(id);
        if (deletedCount != 1) {
            throw new IllegalArgumentException("[ERROR] 삭제 요청 실패");
        }
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("[ERROR] id가 올바르지 않습니다.");
        }
    }
}
