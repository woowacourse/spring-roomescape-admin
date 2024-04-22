package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.TimeCreateRequest;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeDao;

import java.util.List;

@Service
public class ReservationTimeService {

    private static final String RESERVATION_TIME_NOT_FOUND = "존재하지 않는 예약 시간입니다.";

    private final ReservationTimeDao reservationTimeDao;

    @Autowired
    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTime createTime(TimeCreateRequest request) {
        ReservationTime reservationTime = request.toReservationTime();
        return reservationTimeDao.save(reservationTime);
    }

    public ReservationTime readReservationTime(Long id) {
        return reservationTimeDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(RESERVATION_TIME_NOT_FOUND));
    }

    public List<ReservationTime> readReservationTimes() {
        return reservationTimeDao.findAll();
    }

    public void deleteTime(Long id) {
        reservationTimeDao.deleteById(id);
    }
}
