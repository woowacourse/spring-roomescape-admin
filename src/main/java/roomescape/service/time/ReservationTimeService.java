package roomescape.service.time;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.domain.time.ReservationTime;
import roomescape.dto.time.ReservationTimeCreateRequest;
import roomescape.dto.time.ReservationTimeResponse;
import roomescape.exception.NotFoundException;
import roomescape.repository.time.ReservationTimeDao;

@Component
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimeDao.findAll().stream()
            .map(ReservationTimeResponse::from)
            .toList();
    }

    public ReservationTimeResponse create(ReservationTimeCreateRequest reservationTimeCreateRequest) {
        ReservationTime reservationTime = new ReservationTime(reservationTimeCreateRequest.startAt());
        reservationTimeDao.save(reservationTime);

        return ReservationTimeResponse.from(reservationTime);
    }

    public void deleteById(long id) {
        boolean isDeleted = reservationTimeDao.deleteById(id);
        if (!isDeleted) {
            throw new NotFoundException("존재하지 않는 예약 시간입니다.");
        }
    }

    public ReservationTime findById(long id) {
        return reservationTimeDao.findById(id)
            .orElseThrow(() -> new NotFoundException("존재하지 않는 예약 가능 시간입니다."));
    }
}
