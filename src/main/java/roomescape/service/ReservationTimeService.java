package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.DeletedCount;
import roomescape.domain.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.domain.ReservationTime;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::of)
                .toList();
    }

    public ReservationTimeResponse save(ReservationTimeRequest reservationTimeRequest) {
        if (reservationTimeRequest.startAt() == null) {
            throw new IllegalArgumentException("시간이 입력되지 않았습니다. 시간을 입력해주세요.");
        }
        ReservationTime reservationTime = reservationTimeRequest.toReservationTime();
        long id = reservationTimeDao.save(reservationTime);
        return findById(id);
    }

    private ReservationTimeResponse findById(long id) {
        ReservationTime reservationTime = reservationTimeDao.findById(id);
        return ReservationTimeResponse.of(reservationTime);
    }

    public void deleteById(long id) {
        DeletedCount deletedCount = reservationTimeDao.deleteById(id);
        if (deletedCount.isNotDelete()) {
            throw new IllegalArgumentException("해당 id는 존재하지 않습니다. id = %d".formatted(id));
        }
    }

    public void deleteAll() {
        reservationTimeDao.deleteAll();
    }
}
