package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.repository.ReservationTimeDao;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    @Autowired
    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    public ReservationTime findById(Long id) {
        return reservationTimeDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간 id입니다."));
    }

    public ReservationTime save(ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeDao.save(reservationTimeRequest.toReservationTime());
    }

    public void deleteById(Long id) {
        reservationTimeDao.deleteById(id);
    }
}
