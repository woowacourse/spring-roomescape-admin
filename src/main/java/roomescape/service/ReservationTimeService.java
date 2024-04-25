package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeFindResponse;
import roomescape.repository.ReservationTimeDao;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeFindResponse> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeFindResponse::from)
                .toList();
    }

    public ReservationTime findById(Long id) {
        return reservationTimeDao.findById(id);
    }

    public ReservationTime save(ReservationTime reservationTime) {
        return reservationTimeDao.save(reservationTime);
    }

    public void deleteById(Long id) {
        reservationTimeDao.deleteById(id);
    }
}
