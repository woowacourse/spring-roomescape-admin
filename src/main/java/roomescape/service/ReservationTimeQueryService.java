package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.repository.ReservationTimeDao;

@Service
public class ReservationTimeQueryService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeQueryService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponse> findAllReservationTimes() {
        return reservationTimeDao.findAllReservationTimes().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }
}
