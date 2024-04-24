package roomescape.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import roomescape.core.exception.DeleteReservationTimeException;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationTimeRequest;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;
    private final ReservationDao reservationDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao, ReservationDao reservationDao) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationDao = reservationDao;
    }

    public List<ReservationTime> readAll() {
        return reservationTimeDao.findAll();
    }

    public ReservationTime createTime(CreateReservationTimeRequest request) {
        int createdId = reservationTimeDao.create(request);
        return new ReservationTime(createdId, request.startAt());
    }

    public void deleteTime(int id) {
        List<Reservation> reservations = reservationDao.findAllByTimeId(id);
        if (!reservations.isEmpty()) {
            throw new DeleteReservationTimeException(HttpStatus.BAD_REQUEST, "해당 예약시간에 예약이 등록되어 있습니다.");
        }
        reservationTimeDao.delete(id);
    }
}
