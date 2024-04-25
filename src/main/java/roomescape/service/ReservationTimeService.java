package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.entity.ReservationTime;
import roomescape.dao.H2ReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final H2ReservationTimeDao h2ReservationTimeDao;

    public ReservationTimeService(H2ReservationTimeDao h2ReservationTimeDao) {
        this.h2ReservationTimeDao = h2ReservationTimeDao;
    }

    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> reservationTimes = h2ReservationTimeDao.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::of)
                .toList();
    }

    public ReservationTimeResponse save(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toReservationTime();
        long id = h2ReservationTimeDao.save(reservationTime);
        return findById(id);
    }

    private ReservationTimeResponse findById(long id) {
        ReservationTime reservationTime = h2ReservationTimeDao.findById(id);
        return ReservationTimeResponse.of(reservationTime);
    }

    public void deleteById(long id) {
        int isDelete = h2ReservationTimeDao.deleteById(id);
        if (isDelete < 1) {
            throw new IllegalArgumentException("해당 id는 존재하지 않습니다.");
        }
    }
}
