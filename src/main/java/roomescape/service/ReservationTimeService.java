package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

@Service
public class ReservationTimeService {

    private static final int NOT_EFFECTED_ROW_COUNT = 0;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponse> readAll() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }
    
    public ReservationTimeResponse create(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toReservationTime();
        ReservationTime reservationTimeWithId = reservationTimeDao.insert(reservationTime);
        return ReservationTimeResponse.from(reservationTimeWithId);
    }

    public void deleteById(long id) {
        int effectedRowCount = reservationTimeDao.deleteById(id);
        if (effectedRowCount == NOT_EFFECTED_ROW_COUNT) {
            throw new IllegalArgumentException("id가 존재하지 않습니다.");
        }
    }
}
