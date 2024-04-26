package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.ReservationTimeDao;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponse saveTime(final ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeDao.save(reservationTimeRequest);
        return ReservationTimeResponse.from(reservationTime);
    }

    public List<ReservationTimeResponse> findReservationTimes() {
        List<ReservationTimeResponse> reservationTimes = getReservationTimes();
        if (reservationTimes.isEmpty()) {
            throw new IllegalStateException("[ERROR] 방탈출 예약이 가능한 시간이 없습니다.");
        }
        return reservationTimes;
    }

    public List<ReservationTimeResponse> getReservationTimes() {
        return reservationTimeDao.getAll()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public void deleteTime(final long id) {
        reservationTimeDao.delete(id);
    }

}
