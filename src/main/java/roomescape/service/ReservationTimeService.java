package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;
import roomescape.service.dto.ReservationTimeServiceRequest;
import roomescape.service.dto.ReservationTimeServiceResponse;

@Service
@Transactional
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;
    private final ReservationDao reservationDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao, ReservationDao reservationDao) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationDao = reservationDao;
    }

    public ReservationTimeServiceResponse create(ReservationTimeServiceRequest reservationTimeServiceRequest) {
        ReservationTime reservationTime = reservationTimeServiceRequest.toReservationTime();
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);

        return ReservationTimeServiceResponse.from(savedReservationTime);
    }

    @Transactional(readOnly = true)
    public List<ReservationTimeServiceResponse> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeServiceResponse::from)
                .toList();
    }

    public void delete(long id) {
        ReservationTime reservationTime = reservationTimeDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Time not found"));

        reservationDao.deleteByTimeId(reservationTime.getId());
        reservationTimeDao.delete(reservationTime);
    }
}
