package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeDao;

@Service
@Transactional
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @Transactional(readOnly = true)
    public List<ReservationTimeResponse> findAll() {
        return reservationTimeDao.findAll()
            .stream()
            .map(ReservationTimeResponse::from)
            .toList();
    }

    public ReservationTimeResponse create(ReservationTimeRequest timeRequest) {
        Long savedId = reservationTimeDao.save(timeRequest);
        ReservationTime reservationTime = ReservationTime.of(savedId, timeRequest.startAt());
        return ReservationTimeResponse.from(reservationTime);
    }

    public void delete(Long id) {
        reservationTimeDao.deleteById(id);
    }
}
