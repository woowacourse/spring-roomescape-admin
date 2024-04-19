package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;
import roomescape.service.dto.ReservationTimeServiceRequest;
import roomescape.service.dto.ReservationTimeServiceResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;
    private final ReservationDao reservationDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao, ReservationDao reservationDao) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationDao = reservationDao;
    }

    public ReservationTimeServiceResponse create(ReservationTimeServiceRequest reservationTimeServiceRequest) {
        ReservationTime reservationTime = reservationTimeServiceRequest.toReservationTime();
        reservationTimeDao.save(reservationTime);

        return ReservationTimeServiceResponse.from(reservationTime);
    }

    public List<ReservationTimeServiceResponse> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeServiceResponse::from)
                .toList();
    }

    public void delete(Long id) {
        reservationDao.deleteByTimeId(id);
        reservationTimeDao.deleteById(id);
    }
}
