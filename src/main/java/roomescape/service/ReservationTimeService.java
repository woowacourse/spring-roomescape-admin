package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeAddRequest;
import roomescape.repository.ReservationTimeDao;

@Service
public class ReservationTimeService {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    public List<ReservationTime> findAllReservationTime() {
        return reservationTimeDao.findAll();
    }

    public ReservationTime addReservation(ReservationTimeAddRequest reservationTimeAddRequest) {
        return reservationTimeDao.insert(reservationTimeAddRequest);
    }

    public boolean removeReservationTime(Long id) {
        return reservationTimeDao.deleteById(id) > 0;
    }
}
