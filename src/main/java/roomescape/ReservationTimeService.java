package roomescape;

import org.springframework.stereotype.Service;

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

    public ReservationTime create(ReservationTime reservationTime) {
        Long id = reservationTimeDao.insert(reservationTime);
        return reservationTimeDao.findBy(id);
    }

    public void delete(Long id) {
        int deletedCount = reservationTimeDao.delete(id);
        if (deletedCount != 1) {
            throw new IllegalArgumentException("[ERROR] 삭제 요청 실패");
        }
    }
}
