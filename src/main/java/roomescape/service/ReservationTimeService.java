package roomescape.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.db.ReservationDao;
import roomescape.db.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;


@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;
    private final ReservationDao reservationDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao, final ReservationDao reservationDao) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationDao = reservationDao;
    }

    public ReservationTime create(final ReservationTimeRequest reservationTimeRequest) {
        final ReservationTime reservationTime = new ReservationTime(reservationTimeRequest.startAt());
        return reservationTimeDao.save(reservationTime);
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    public void deleteById(final long id) {
        validateTimeIdExist(id);
        reservationTimeDao.deleteById(id);
    }

    private void validateTimeIdExist(final long id) {
        if (reservationDao.findTimeId(id).isEmpty()) {
            throw new IllegalArgumentException("예약된 id가 있어 지울 수 없습니다");
        }
    }
}
