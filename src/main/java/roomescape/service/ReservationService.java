package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public Reservation create(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = reservationTimeDao.findById(reservationRequest.timeId());
        return reservationDao.save(reservationRequest, reservationTime);
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public Reservation findById(Long id) {
        return reservationDao.findById(id);
    }

    public void deleteById(Long id) {
        reservationDao.deleteById(id);
    }
}
