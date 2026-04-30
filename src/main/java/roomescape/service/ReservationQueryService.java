package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.ReservationDao;

@Service
public class ReservationQueryService {

    private final ReservationDao reservationDao;

    public ReservationQueryService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationDao.findAllReservations().stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
