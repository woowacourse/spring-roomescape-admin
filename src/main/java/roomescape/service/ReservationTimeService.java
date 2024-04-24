package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeRequest;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeDao;
import roomescape.repository.ReservationTimeRegisterDetail;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao timeDao;

    public ReservationTimeService(ReservationTimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public ReservationTime addReservationTime(ReservationTimeRequest request) {
        ReservationTimeRegisterDetail registerDetail = new ReservationTimeRegisterDetail(request);

        return timeDao.save(registerDetail);
    }

    public List<ReservationTime> findAllReservationTimes() {
        return timeDao.findAll();
    }

    public void deleteReservationTimeById(long id) {
        timeDao.deleteById(id);
    }
}
