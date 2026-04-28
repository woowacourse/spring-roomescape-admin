package roomescape.reservationTime;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservationTime.dto.ReservationTimeRequest;
import roomescape.reservationTime.dto.ReservationTimeResponse;

@Service
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

    public ReservationTimeResponse save(ReservationTimeRequest request) {
        ReservationTime saved = reservationTimeDao.save(request.startAt());
        return ReservationTimeResponse.from(saved);
    }

    public void delete(Long id) {
        reservationTimeDao.delete(id);
    }
}