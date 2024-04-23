package roomescape.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeSaveDto;
import roomescape.entity.ReservationTime;

@Repository
public class ReservationTimeRepository {

    private final ReservationTimeDao reservationTimeDao;

    @Autowired
    public ReservationTimeRepository(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAllReservationTimes();
    }

    public ReservationTime save(ReservationTimeSaveDto reservationTimeDto) {
        long id = reservationTimeDao.save(reservationTimeDto);
        return reservationTimeDao.findById(id);
    }

    public void delete(long id) {
        reservationTimeDao.delete(id);
    }
}
