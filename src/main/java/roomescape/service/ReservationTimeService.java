package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.dto.response.ReservationTimeResponse;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimeDao.findAll()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse findById(long id) {
        ReservationTime reservationTime = reservationTimeDao.findById(id);

        return ReservationTimeResponse.from(reservationTime);
    }

    public ReservationTimeResponse save(ReservationTimeCreateRequest request) {
        ReservationTime saved = reservationTimeDao.save(request.toReservationTime());

        return ReservationTimeResponse.from(saved);
    }

    public void deleteById(long id) {
        reservationTimeDao.deleteById(id);
    }
}
