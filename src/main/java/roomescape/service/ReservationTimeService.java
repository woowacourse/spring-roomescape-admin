package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    public ReservationTime findById(Long id) {
        return reservationTimeDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "존재하지 않는 시간입니다: timeId=" + id
                ));
    }

    public ReservationTime add(ReservationTimeRequest request) {
        ReservationTime reservationTime = ReservationTime.ofNew(request.startAt());
        return reservationTimeDao.save(reservationTime);
    }

    public void delete(Long id) {
        reservationTimeDao.deleteById(id);
    }
}
