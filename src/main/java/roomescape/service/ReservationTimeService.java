package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTime save(ReservationTimeRequest request) {
        Long id = reservationTimeDao.save(request.toEntity());
        return reservationTimeDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간입니다."));
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    public void delete(Long id) {
        ReservationTime reservationTime = reservationTimeDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간입니다."));
        reservationTimeDao.deleteById(reservationTime.getId());
    }
}
