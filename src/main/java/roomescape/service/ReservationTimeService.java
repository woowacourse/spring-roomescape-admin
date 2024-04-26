package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<TimeResponse> getAllReservationTimes() {
        return reservationTimeDao.findAll().stream()
                .map(TimeResponse::new)
                .toList();
    }

    public TimeResponse addReservationTime(TimeRequest timeRequest) {
        return new TimeResponse(reservationTimeDao.add(timeRequest));
    }

    public void deleteReservationTime(Long id) {
        reservationTimeDao.delete(id);
    }
}
