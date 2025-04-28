package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponse create(ReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(request.startAt());
        reservationTime = reservationTimeDao.create(reservationTime);
        return ReservationTimeResponse.from(reservationTime);
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimeDao.findAll()
            .stream()
            .map(ReservationTimeResponse::from)
            .toList();
    }

    public void deleteReservationTime(Long id) {
        int deleteCount = reservationTimeDao.deleteById(id);
        if (deleteCount == 0) {
            throw new IllegalArgumentException("해당 id가 존재하지 않습니다");
        }
    }
}
