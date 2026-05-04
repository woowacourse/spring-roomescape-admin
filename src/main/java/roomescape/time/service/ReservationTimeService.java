package roomescape.time.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.repository.ReservationJdbcDao;
import roomescape.time.domain.ReservationTime;
import roomescape.time.repository.ReservationTimeJdbcDao;

@Service
@Transactional
public class ReservationTimeService {

    private final ReservationJdbcDao reservationJdbcDao;
    private final ReservationTimeJdbcDao reservationTimeJdbcDao;

    public ReservationTimeService(ReservationJdbcDao reservationJdbcDao,
                                  ReservationTimeJdbcDao reservationTimeJdbcDao) {
        this.reservationJdbcDao = reservationJdbcDao;
        this.reservationTimeJdbcDao = reservationTimeJdbcDao;
    }

    @Transactional(readOnly = true)
    public List<ReservationTime> findAll() {
        return reservationTimeJdbcDao.findAll();
    }

    public ReservationTime save(ReservationTime reservationTime) {
        Long savedId = reservationTimeJdbcDao.save(reservationTime);
        return ReservationTime.create(savedId, reservationTime.getStartAt());
    }

    public int deleteById(Long id) {
        List<Reservation> reservations = reservationJdbcDao.findByTimeId(id);
        if (!reservations.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 해당 시간에 예약이 존재하여 삭제할 수 없습니다.");
        }

        return reservationTimeJdbcDao.deleteById(id);
    }
}
