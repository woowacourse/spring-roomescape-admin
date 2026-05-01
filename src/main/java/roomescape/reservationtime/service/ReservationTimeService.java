package roomescape.reservationtime.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.exception.ReservationException;
import roomescape.reservation.exception.ReservationExceptionCode;
import roomescape.reservationtime.dao.ReservationTimeDao;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.CreateReservationTimeRequest;
import roomescape.reservationtime.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;
    private final ReservationDao reservationDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao, ReservationDao reservationDao) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationDao = reservationDao;
    }

    //TODO: 시간 중복 생성 방지, 잘못된 시간 형식 방지
    public ReservationTimeResponse save(CreateReservationTimeRequest request) {
        request.validate();
        ReservationTime reservationTime = request.toReservationTime();
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);
        return new ReservationTimeResponse(savedReservationTime);
    }

    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimeDao.findAllReservationTimes();
    }

    public void delete(Long reservationTimeId) {
        Optional<ReservationTime> reservationTime = reservationTimeDao.findById(reservationTimeId);
        validateReservationNotExistsBy(reservationTimeId);
        reservationTime.ifPresent(reservationTimeDao::delete);
    }

    private void validateReservationNotExistsBy(Long reservationTimeId) {
        if (reservationDao.existsByReservationTime(reservationTimeId)) {
            throw new ReservationException(ReservationExceptionCode.RESERVATION_ALREADY_EXISTS);
        }
    }
}
