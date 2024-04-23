package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeAddRequest;
import roomescape.repository.ReservationTimeDao;

@Service
public class ReservationTimeService {

    private ReservationTimeDao reservationTimeDao;

    ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> findAllReservationTime() {
        return reservationTimeDao.findAll();
    }

    public ReservationTime addReservationTime(ReservationTimeAddRequest reservationTimeAddRequest) {
        return reservationTimeDao.insert(reservationTimeAddRequest);
    }

    public boolean removeReservationTime(Long id) {
        return reservationTimeDao.deleteById(id) > 0;
    }
}
