package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.reservationtime.ReservationTimeRequest;
import roomescape.dto.reservationtime.ReservationTimeResponse;

@Service
public class TimeService {

    private final ReservationTimeDao reservationTimeDao;
    private final ReservationDao reservationDao;

    public TimeService(ReservationTimeDao reservationTimeDao, ReservationDao reservationDao) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationDao = reservationDao;
    }

    public List<ReservationTimeResponse> times() {
        return reservationTimeDao.findAllReservationTimes()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse createTime(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toReservationTime();

        long id = reservationTimeDao.saveReservationTime(reservationTime);
        ReservationTimeResponse reservationTimeResponse = new ReservationTimeResponse(
                id,
                reservationTimeRequest.startAt().toString());

        return reservationTimeResponse;
    }

    public void deleteTime(Long id) {
        if (reservationDao.hasReservationOf(id)) {
            throw new IllegalStateException("해당 시간에 예약이 있어 삭제할 수 없습니다.");
        }
        reservationTimeDao.deleteReservationTime(id);
    }
}
