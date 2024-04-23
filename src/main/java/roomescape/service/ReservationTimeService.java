package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.ReservationTimeDao;

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

    public ReservationTimeResponse create(ReservationTimeRequest reservationTimeRequest) {
        Long savedId = reservationTimeDao.save(reservationTimeRequest);
        return ReservationTimeResponse.of(savedId, reservationTimeRequest);
    }

    public void delete(Long id) {
        reservationTimeDao.deleteById(id);
    }
}
